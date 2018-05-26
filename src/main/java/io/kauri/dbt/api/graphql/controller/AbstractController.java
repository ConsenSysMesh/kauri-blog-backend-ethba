package io.kauri.dbt.api.graphql.controller;


import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.ExecutionResultImpl;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.GraphQLSchema;
import io.kauri.dbt.model.exception.DBTException;
import io.kauri.dbt.api.graphql.utils.ExecutionResultWithoutEmptyArray;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

public abstract class AbstractController extends TextWebSocketHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
    protected static final ObjectMapper mapper = new ObjectMapper(); 
    protected static final TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};

    protected final GraphQL graphQlFromAnnotated;

    @Autowired
    protected AbstractController(Object...services) {

        //Schema generated from query classes
        GraphQLSchemaGenerator generator = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder(), //Resolve by annotations
                        new PublicResolverBuilder("net.consensys.flow.knowledgebase")) //Resolve public methods inside root package
                .withValueMapperFactory(new JacksonValueMapperFactory());
        
        for(Object service: services) {
            generator.withOperationsFromSingleton(service);
        }
        
        GraphQLSchema schemaFromAnnotated = generator.generate();
        
        graphQlFromAnnotated = GraphQL.newGraphQL(schemaFromAnnotated).build();
        
        LOGGER.info("Generated GraphQL schema using SPQR");
    }

    /**
     * Execute a GraphQL query 
     * @param context   Context object (Authentication token, WebSocket Session) 
     * @param request   Request (contaning query, variables, operation, ...)
     * @return          Response
     */
    @SuppressWarnings("unchecked")
    protected Object indexFromAnnotated(Object context, @RequestBody Map<String, Object> request) {
        
        Map<String, Object> variables = (Map<String, Object>) request.getOrDefault("variables", Collections.emptyMap());

        ExecutionInput input = ExecutionInput.newExecutionInput()
                .query((String) request.get("query"))
                .operationName((String) request.get("operationName"))
                .variables(variables)
                .context(context)
                .build();    
        
        ExecutionResult executionResult = graphQlFromAnnotated.execute(input);

        ExecutionResult result = new ExecutionResultWithoutEmptyArray(
                executionResult.getData(), 
                executionResult.getErrors(),
                executionResult.getExtensions());
        
        if (!result.getErrors().isEmpty()) {
            return sanitize(result);
        }

        return result;
    }

    /**
     * Just mocking error handling
     * @param executionResult
     * @return executionResult
     */
    protected ExecutionResult sanitize(ExecutionResult executionResult) {
        return new ExecutionResultImpl(executionResult.getErrors().stream()
                .peek(err -> LOGGER.error(err.getMessage()))
                .map(this::sanitize)
                .collect(Collectors.toList()));
    }
    
    /**
     * Sanitize correctly a service exception
     * @param GraphQLError error
     * @return GraphQLError error Sanitized
     */
    protected GraphQLError sanitize(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            return new GraphQLError() {
                @Override
                public String getMessage() {
                    Throwable exception = ((ExceptionWhileDataFetching) error).getException();
                    Throwable cause = exception.getCause();
                    if(cause == null) {
                        return exception.toString();
                    }
                    if(cause instanceof InvocationTargetException) {
                        InvocationTargetException e = (InvocationTargetException) cause;
                        if(e.getTargetException() instanceof DBTException) {
                            DBTException be = (DBTException) e.getTargetException();
                            return be.getMessage();
                        }
                    }
                    return cause.getMessage();
                }

                @Override
                public List<SourceLocation> getLocations() {
                    return error.getLocations();
                }

                public ErrorType getErrorType() {
                    return error.getErrorType();
                }
            };
        }
        return error;
    }
}
