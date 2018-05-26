package io.kauri.dbt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.kauri.dbt.model.Document;
import io.kauri.dbt.model.Status;
import io.kauri.dbt.model.dto.Blog;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.dto.filter.BlogPostFilter;
import io.kauri.dbt.service.repository.BlogPostIPFSRepository;

@Service
public class BlogService {

    private BlogPostIPFSRepository blogPostRepository;
    
    @Autowired
    public BlogService(BlogPostIPFSRepository repository) {
        this.blogPostRepository = repository;
    }
    
    public Page<BlogPost> searchBlogPost(PageRequest pagination, BlogPostFilter filter) {
        return blogPostRepository.findByFilter(pagination, filter);
    }
    
    public BlogPost getBlogPost(String id) {
        Document doc = blogPostRepository.findOne(id);
        
        //TODO build BlogPost;
        
        return null;
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
        
        doc = blogPostRepository.save(doc, indexFields);
        
        return doc.getId();
    }
}
