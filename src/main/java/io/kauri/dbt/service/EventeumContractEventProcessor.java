package io.kauri.dbt.service;

import io.kauri.dbt.message.details.EventeumEventDetails;

public interface EventeumContractEventProcessor {

    void processEvent(EventeumEventDetails eventDetails);
}
