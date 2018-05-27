package io.kauri.dbt.service;

import io.kauri.dbt.constant.ContractEvents;
import io.kauri.dbt.message.details.EventeumEventDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class DefaultEventeumContractEventProcessor implements EventeumContractEventProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultEventeumContractEventProcessor.class);

    private Map<String, Consumer<EventeumEventDetails>> messageConsumers;

    @Autowired
    public DefaultEventeumContractEventProcessor(SmartContractEventService smartContractEventService) {
        messageConsumers = new HashMap<>();

        messageConsumers.put(ContractEvents.POST_SUBMITTED, (eventDetails) -> {
            smartContractEventService.onPostSubmittedEvent(eventDetails);
        });

        messageConsumers.put(ContractEvents.POST_TIPPED, (eventDetails) -> {
            smartContractEventService.onPostTippedEvent(eventDetails);
        });

        messageConsumers.put(ContractEvents.BLOG_TIPPED, (eventDetails) -> {
            smartContractEventService.onBlogTippedEvent(eventDetails);
        });
    }

    @Override
    public void processEvent(EventeumEventDetails eventDetails) {
        final Consumer<EventeumEventDetails> consumer = messageConsumers.get(eventDetails.getName());

        if (consumer == null) {
            LOG.error("Received an unknown event on the kafka topic of type: " + eventDetails.getName());
        }

        consumer.accept(eventDetails);
    }
}
