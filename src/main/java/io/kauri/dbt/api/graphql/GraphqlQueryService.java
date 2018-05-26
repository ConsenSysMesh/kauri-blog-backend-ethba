package io.kauri.dbt.api.graphql;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.dto.BlogPostFilter;
import io.kauri.dbt.model.exception.DBTException;
import io.kauri.dbt.service.BlogService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLRootContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GraphqlQueryService {

    private static final String DEFAULT_PAGE_SIZE = "20";  
    private static final String DEFAULT_PAGE_NO   = "0"; 

    private BlogService blogService;
    
    @Autowired
    public GraphqlQueryService(BlogService blogService) {
        this.blogService = blogService;
    }

    @GraphQLQuery(name = "getBlogPost")
    public BlogPost getBlogPost(@GraphQLArgument(name = "id") String id) throws DBTException {
        return blogService.getBlogPost(id);
    }
    
    @GraphQLQuery(name = "searchBlogPost")
    public Page<BlogPost> searchBlogPost(
            @GraphQLArgument(name = "filter") BlogPostFilter filter,
            @GraphQLArgument(name = "page", defaultValue = DEFAULT_PAGE_SIZE) int pageNo,
            @GraphQLArgument(name = "size", defaultValue = DEFAULT_PAGE_NO) int pageSize,
            @GraphQLArgument(name = "sort") String sortAttribute,
            @GraphQLArgument(name = "dir") Sort.Direction sortDirection) throws DBTException{ 
        
        log.debug("searchBlogPost");
        log.debug(filter.toString());
        
        PageRequest pagination = new PageRequest(pageNo, pageSize, new Sort((sortDirection!=null)?sortDirection:Sort.Direction.DESC, (sortAttribute!=null)?sortAttribute:"dateCreated"));

        return blogService.searchBlogPost(pagination, filter);
    }
    
}
