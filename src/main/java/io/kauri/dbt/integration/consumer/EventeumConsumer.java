package io.kauri.dbt.integration.consumer;

import io.kauri.dbt.message.EventeumMessage;

public interface EventeumConsumer {

    void onEventeumMessage(EventeumMessage message);
}
