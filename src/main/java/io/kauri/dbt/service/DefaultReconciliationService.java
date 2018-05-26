package io.kauri.dbt.service;

import org.springframework.stereotype.Service;

import io.kauri.dbt.message.details.EventeumEventDetails;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.exception.DBTException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultReconciliationService implements ReconciliationService {
    
    private final BlogService blogService;
    
    public DefaultReconciliationService(BlogService blogService) {
        this.blogService = blogService;
    }
    
    @Override
    public void reconcilePostSubmittedEvent(EventeumEventDetails details) {
        try {
            BlogPost post = blogService.getBlogPost(details.getIndexedParameters().get(0).getValueString());
            blogService.publish(post);
            
        } catch (DBTException e) {
            log.error("Error whilst the reconcilation", e);
        }
    }
}
