package io.kauri.dbt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import io.kauri.dbt.model.dto.Blog;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.dto.BlogPostFilter;
import io.kauri.dbt.model.exception.DBTException;

public interface BlogService {

    public Page<BlogPost> searchBlogPost(PageRequest pagination, BlogPostFilter filter) throws DBTException;

    public BlogPost getBlogPost(String id) throws DBTException;

    public Blog getBlog(String id) throws DBTException;

    public void createBlog(Blog blog) throws DBTException;

    public BlogPost saveDraft(BlogPost post) throws DBTException;

    public BlogPost publish(BlogPost post) throws DBTException;

    BlogPost incrementPostTips(BlogPost post, double tipAmount) throws DBTException;

    Blog incrementBlogTips(Blog blog, double tipAmount) throws DBTException;
}
