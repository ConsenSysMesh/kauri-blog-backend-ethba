package io.kauri.dbt.api.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.kauri.dbt.model.Status;
import io.kauri.dbt.model.dto.Blog;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.exception.DBTException;
import io.kauri.dbt.service.BlogService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;

@Service
public class GraphqlMutationService {

    private BlogService blogService;
    
    @Autowired
    public GraphqlMutationService(BlogService blogService) {
        this.blogService = blogService;
    }
    
    @GraphQLMutation(name = "createBlog")
    public Blog createBlog(
            @GraphQLArgument(name = "user") String user,
            @GraphQLArgument(name = "name") String name) throws DBTException {

        Blog blog = new Blog(user, name);
        
        //TODO store blog in mongo db
        
        return blog;
    }
    

    @GraphQLMutation(name = "savePost")
    public BlogPost savePost(
            @GraphQLArgument(name = "id") String id,
            @GraphQLArgument(name = "user") String user,
            @GraphQLArgument(name = "title") String title,
            @GraphQLArgument(name = "content") String content) throws DBTException {
 
        BlogPost post = null;
        if(StringUtils.isEmpty(id)) { // new blogpost
            post = new BlogPost(user, title, content);      
        } else { // update
            post = new BlogPost(id, user, title, content);   
        }
        
        blogService.submitBlogPost(post);
        
        return post;
    } 
}
