package io.kauri.dbt.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.kauri.dbt.model.Document;
import io.kauri.dbt.model.Status;
import io.kauri.dbt.model.dto.Blog;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.dto.BlogPostFilter;
import io.kauri.dbt.model.exception.DBTException;
import io.kauri.dbt.service.repository.BlogMongoRepository;
import io.kauri.dbt.service.repository.BlogPostIPFSRepository;
import lombok.extern.slf4j.Slf4j;
import net.consensys.tools.ipfs.ipfsstore.dto.Metadata;
import net.consensys.tools.ipfs.ipfsstore.exception.NotFoundException;
import net.consensys.tools.ipfs.ipfsstore.exception.ServiceException;
import net.consensys.tools.ipfs.ipfsstore.service.StoreService;

@Service
@Slf4j
public class DefaultBlogService implements BlogService {

    private final BlogPostIPFSRepository blogPostRepository;
    private final BlogMongoRepository blogMongoRepository;
    private final StoreService storeService;
    
    @Autowired
    public DefaultBlogService(BlogPostIPFSRepository repository, StoreService storeService, BlogMongoRepository blogMongoRepository) {
        this.blogPostRepository = repository;
        this.storeService = storeService;
        this.blogMongoRepository = blogMongoRepository;
    }
    
    public Page<BlogPost> searchBlogPost(PageRequest pagination, BlogPostFilter filter) throws DBTException  {
        log.debug("searchBlogPost");
        Page<Document> documents = blogPostRepository.findByFilter(pagination, filter);

        return new PageImpl<>(
                documents.getContent().stream().map(doc -> {
                    try {
                        return convert(doc);
                    } catch (DBTException e) {
                        log.error(e.getMessage(), e);
                        return null;
                    }
                }).collect(Collectors.toList()), 
                new PageRequest(documents.getNumber(), documents.getSize(), documents.getSort()), 
                documents.getTotalElements());
    }
    
    public BlogPost getBlogPost(String id) throws DBTException  {
        return convert(blogPostRepository.findOne(id));
    }

    public Blog getBlog(String id) throws DBTException  {
        return blogMongoRepository.findOne(id);
    }
    
    public void createBlog(Blog blog) throws DBTException  {
        blogMongoRepository.save(blog);
    }
    
    public BlogPost saveDraft(BlogPost post) throws DBTException {
        
        if(!StringUtils.isEmpty(post.getId())){
            BlogPost current = this.getBlogPost(post.getId());
            if(current.getStatus().equals(Status.PUBLISHED)) {
                throw new DBTException("Blog post "+post.getId()+" already published"); 
            } 
        }
        
        return this.save(post, Status.DRAFT);
    }
    
    public BlogPost publish(BlogPost post) throws DBTException {
        BlogPost current = this.getBlogPost(post.getId());
        if(current.getStatus().equals(Status.PUBLISHED)) {
            throw new DBTException("Blog post "+post.getId()+" already published"); 
        }
        
        return this.save(post, Status.PUBLISHED);
    }

    public BlogPost incrementPostTips(BlogPost post, double tipAmount) throws DBTException {
        incrementBlogTips(getBlog(post.getUser()), tipAmount);

        BlogPost current = this.getBlogPost(post.getId());

        current.setTotalTips(current.getTotalTips() + tipAmount);

        return save(current, current.getStatus());
    }
    
    public Blog incrementBlogTips(Blog blog, double tipAmount) throws DBTException {
        Blog current = this.getBlog(blog.getUser());

        current.setTotalTips(current.getTotalTips() + tipAmount);

        return blogMongoRepository.save(current);
    }

    private BlogPost save(BlogPost post, Status status) throws DBTException {
        
        Document doc = new Document();
        doc.setContent(post.getContent());
        doc.setTitle(post.getTitle());
        doc.setUser(post.getUser());
        doc.setId(post.getId());
        
        
        Map<String, Object> indexFields = new HashMap<>();
        indexFields.put("status", status);
        if(StringUtils.isEmpty(post.getId())) {
            indexFields.put("dateCreated", new Date());
        }
        indexFields.put("dateUpdated", new Date());
        indexFields.put("totalTips", post.getTotalTips());
        
        doc = blogPostRepository.save(doc, indexFields);
        
        return this.getBlogPost(doc.getId());
    }
    
    private BlogPost convert(Document doc) throws DBTException  {
        
        try {
            BlogPost post = new BlogPost();
            post.setId(doc.getId());
            post.setContent(doc.getContent());
            post.setTitle(doc.getTitle());
            post.setUser(doc.getUser());
            
            Metadata meta = storeService.getFileMetadataById("blogpost", doc.getId());
            
            post.setContentHash(meta.getHash());
            post.setStatus(Status.valueOf((String) meta.getIndexFieldValue("status")));
            
            Timestamp dateCreatedStamp = new Timestamp((Long) meta.getIndexFieldValue("dateCreated"));
            Date dateCreated = new Date(dateCreatedStamp.getTime());
            post.setDateCreated(dateCreated);
            
            Timestamp dateUpdatedStamp = new Timestamp((Long) meta.getIndexFieldValue("dateUpdated"));
            Date dateUpdated = new Date(dateUpdatedStamp.getTime());
            post.setDateUpdated(dateUpdated);

            post.setTotalTips((Double) meta.getIndexFieldValue("totalTips"));
            post.setBlogName(blogMongoRepository.findOneByUser(doc.getUser()).getName());
            
            return post;
            
        } catch (ServiceException e) {
            throw new DBTException("Error during convertion", e);
        } catch (NotFoundException e) {
            throw new DBTException("Document not found", e);
        }
    }
    
}
