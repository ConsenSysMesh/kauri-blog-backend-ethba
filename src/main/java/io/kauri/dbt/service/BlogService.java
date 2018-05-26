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

import io.kauri.dbt.model.Document;
import io.kauri.dbt.model.Status;
import io.kauri.dbt.model.dto.Blog;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.dto.filter.BlogPostFilter;
import io.kauri.dbt.service.repository.BlogPostIPFSRepository;
import net.consensys.tools.ipfs.ipfsstore.dto.Metadata;
import net.consensys.tools.ipfs.ipfsstore.exception.NotFoundException;
import net.consensys.tools.ipfs.ipfsstore.exception.ServiceException;
import net.consensys.tools.ipfs.ipfsstore.service.StoreService;

@Service
public class BlogService {

    private StoreService storeService;
    private BlogPostIPFSRepository blogPostRepository;
    
    @Autowired
    public BlogService(BlogPostIPFSRepository repository, StoreService storeService) {
        this.blogPostRepository = repository;
        this.storeService = storeService;
    }
    
    public Page<BlogPost> searchBlogPost(PageRequest pagination, BlogPostFilter filter) {
        Page<Document> documents = blogPostRepository.findByFilter(pagination, filter);

        return new PageImpl<>(
                documents.getContent().stream().map(doc -> convert(doc)).collect(Collectors.toList()), 
                new PageRequest(documents.getNumber(), documents.getSize(), documents.getSort()), 
                documents.getTotalElements());
    }
    
    public BlogPost getBlogPost(String id) {
        Document doc = blogPostRepository.findOne(id);

        return convert(doc);
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
    
    

    
    private BlogPost convert(Document doc) {
        
        try {
            BlogPost post = new BlogPost();
            post.setId(doc.getId());
            post.setContent(doc.getContent());
            post.setTitle(doc.getTitle());
            post.setUser(doc.getUser());
            
            Metadata meta = storeService.getFileMetadataById("blogpost", doc.getId());
            
            post.setContentHash(meta.getHash());
            post.setStatus(Status.valueOf((String) meta.getIndexFieldValue("status")));
            
            Timestamp stamp = new Timestamp((Long) meta.getIndexFieldValue("dateCreated"));
            Date dateCreated = new Date(stamp.getTime());
            post.setDateCreated(dateCreated);
            post.setTotalTip((Long) meta.getIndexFieldValue("totalTips"));
            
            return post;
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
