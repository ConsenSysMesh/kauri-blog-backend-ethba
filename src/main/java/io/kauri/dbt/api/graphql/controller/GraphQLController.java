package io.kauri.dbt.api.graphql.controller;


import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.kauri.dbt.api.graphql.GraphqlMutationService;
import io.kauri.dbt.api.graphql.GraphqlQueryService;

@RestController
public class GraphQLController extends AbstractController {

    private static final String PATH = "/graphql";
    
    @Autowired
    public GraphQLController(GraphqlQueryService graphQLQueryService, GraphqlMutationService graphQLMutationService) {
        super(graphQLQueryService, graphQLMutationService);
    }

    @PostMapping(value = PATH, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromAnnotated(Principal principal, @RequestBody Map<String, Object> request) {
        return super.indexFromAnnotated(principal, request);
    }
}