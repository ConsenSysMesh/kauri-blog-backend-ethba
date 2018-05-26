package io.kauri.dbt.integration.consumer;

import io.kauri.dbt.message.EventeumMessage;
import io.kauri.dbt.service.EventeumContractEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventeumConsumer implements EventeumConsumer {

    private EventeumContractEventProcessor eventProcessor;

    @Autowired
    public KafkaEventeumConsumer(EventeumContractEventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @Override
    public void onEventeumMessage(EventeumMessage message) {
        eventProcessor.processEvent(message.getDetails());
    }
}
