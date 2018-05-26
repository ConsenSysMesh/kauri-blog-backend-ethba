package io.kauri.dbt.service;

import io.kauri.dbt.model.dto.eventeum.ContractEventFilter;

public interface EventeumService {

    void addEventFilter(ContractEventFilter eventFilter);
}
