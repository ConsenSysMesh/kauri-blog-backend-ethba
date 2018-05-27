package io.kauri.dbt.service;

import io.kauri.dbt.message.details.EventeumEventDetails;

public interface SmartContractEventService {

    void onPostSubmittedEvent(EventeumEventDetails details);

    void onPostTippedEvent(EventeumEventDetails details);

    void onBlogTippedEvent(EventeumEventDetails details);
}
