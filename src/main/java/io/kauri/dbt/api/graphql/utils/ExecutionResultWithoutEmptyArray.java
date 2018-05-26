package io.kauri.dbt.api.graphql.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import graphql.ExecutionResultImpl;
import graphql.GraphQLError;

/**
 * Extension of ExecutionResultImpl which removes empty errors and empty extensions from the response if null
 * in order to be compliant with the GraphQL specification
 * 
 * @author Gregoire Jeanmart <gregoire.jeanmart@consensys.net>
 *
 */
public class ExecutionResultWithoutEmptyArray extends ExecutionResultImpl {


    public ExecutionResultWithoutEmptyArray(List<? extends GraphQLError> errors) {
        super(errors);
    }

    public ExecutionResultWithoutEmptyArray(Object data, List<? extends GraphQLError> errors, Map<Object, Object> extensions) {
        super(data, errors, extensions);
    }

    public ExecutionResultWithoutEmptyArray(Object data, List<? extends GraphQLError> errors) {
        super(data, errors);
    }

    @Override
    @JsonInclude(Include.NON_EMPTY)
    public List<GraphQLError> getErrors() {
        return super.getErrors();
    }

    @Override
    @JsonInclude(Include.NON_EMPTY)
    public Map<Object, Object> getExtensions() {
        return super.getExtensions();
    }
    
    

    


}
