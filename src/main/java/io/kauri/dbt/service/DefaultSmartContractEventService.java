package io.kauri.dbt.service;

import net.consensys.eventeum.dto.event.parameter.EventParameter;
import io.kauri.dbt.model.dto.Blog;
import org.springframework.stereotype.Service;

import io.kauri.dbt.message.details.EventeumEventDetails;
import io.kauri.dbt.model.dto.BlogPost;
import io.kauri.dbt.model.exception.DBTException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class DefaultSmartContractEventService implements SmartContractEventService {
    
    private final BlogService blogService;
    
    public DefaultSmartContractEventService(BlogService blogService) {
        this.blogService = blogService;
    }
    
    @Override
    public void onPostSubmittedEvent(EventeumEventDetails details) {
        try {
            BlogPost post = getBlogPost(details);
            blogService.publish(post);
            
        } catch (DBTException e) {
            log.error("Error whilst the reconcilation", e);
        }
    }

    @Override
    public void onPostTippedEvent(EventeumEventDetails details) {
        try {
            BlogPost post = getBlogPost(details);

            final BigInteger tipAmount = getBigIntegerParameter(details.getNonIndexedParameters(), 0);
            blogService.incrementPostTips(post, tipAmount.doubleValue());
        } catch (DBTException e) {
            log.error("Error processing PostTipped event", e);
        }
    }

    @Override
    public void onBlogTippedEvent(EventeumEventDetails details) {
        try {
            Blog blog = getBlog(details);

            final BigInteger tipAmount = getBigIntegerParameter(details.getNonIndexedParameters(), 0);
            blogService.incrementBlogTips(blog, tipAmount.doubleValue());
        } catch (DBTException e) {
            log.error("Error processing BlogTipped event", e);
        }
    }

    private BigInteger getBigIntegerParameter(List<EventParameter> parameters, int index) {
        final Object object = parameters.get(0).getValue();

        return (BigInteger) object;
    }

    private BlogPost getBlogPost(EventeumEventDetails details) throws DBTException {
        return blogService.getBlogPost(details.getIndexedParameters().get(0).getValueString());
    }

    private Blog getBlog(EventeumEventDetails details) throws DBTException {
        return blogService.getBlog(details.getIndexedParameters().get(1).getValueString());
    }
}
