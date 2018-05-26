package io.kauri.dbt.service;

import io.kauri.dbt.message.details.EventeumEventDetails;

public interface ReconciliationService {

    void reconcilePostSubmittedEvent(EventeumEventDetails details);
}
