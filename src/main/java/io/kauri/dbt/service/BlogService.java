package io.kauri.dbt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.kauri.dbt.model.Document;
import io.kauri.dbt.model.Status;
import io.kauri.dbt.model.dto.Blog;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.service.repository.BlogIPFSRepository;

@Service
public class BlogService {

    private BlogIPFSRepository repository;
    
    @Autowired
    public BlogService(BlogIPFSRepository repository) {
        this.repository = repository;
    }
    
    public void createBlog(Blog blog) {
        //todo
    }
    
    public String submitBlogPost(BlogPost post) {
        
        Document doc = new Document();
        doc.setContent(post.getContent());
        doc.setTitle(post.getTitle());
        doc.setUser(post.getUser());
        doc.setId(post.getId());
        
        Map<String, Object> indexFields = new HashMap<>();
        indexFields.put("status", Status.DRAFT);
        
        doc = repository.save(doc, indexFields);
        
        return doc.getId();
    }
}
