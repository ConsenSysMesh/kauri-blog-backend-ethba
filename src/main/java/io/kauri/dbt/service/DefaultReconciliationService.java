package io.kauri.dbt.service;

import io.kauri.dbt.message.details.EventeumEventDetails;
import io.kauri.dbt.model.dto.BlogPost;

public class DefaultReconciliationService implements ReconciliationService {
    
    private BlogService blogService;
    public DefaultReconciliationService(BlogService blogService) {
        this.blogService = blogService;
    }
    
    @Override
    public void reconcilePostSubmittedEvent(EventeumEventDetails details) {
        BlogPost post = blogService.getBlogPost(details.getIndexedParameters().get(0).getValueString());
        blogService.publish(post);
    }
}
