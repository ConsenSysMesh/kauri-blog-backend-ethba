package io.kauri.dbt.utils;

import io.kauri.dbt.model.dto.eventeum.ContractEventFilter;
import io.kauri.dbt.model.dto.eventeum.ContractEventSpecification;
import io.kauri.dbt.model.dto.eventeum.ParameterType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ContractEventFilterBuilder {

    private String id;

    private String contractAddress;

    private String eventName;

    private List<ParameterType> indexedParameterTypes;

    private List<ParameterType> nonIndexedParameterTypes;

    public ContractEventFilterBuilder() {
        indexedParameterTypes = new ArrayList<>();
        nonIndexedParameterTypes = new ArrayList<>();
    }

    public ContractEventFilterBuilder contractAddress(String contractAddress) {
        this.contractAddress = contractAddress;

        return this;
    }

    public ContractEventFilterBuilder eventName(String eventName) {
        this.eventName = eventName;

        return this;
    }

    public ContractEventFilterBuilder id(String id) {
        this.id = id;

        return this;
    }

    public ContractEventFilterBuilder indexedParameter(ParameterType parameterType) {
        indexedParameterTypes.add(parameterType);

        return this;
    }

    public ContractEventFilterBuilder nonIndexedParameter(ParameterType parameterType) {
        nonIndexedParameterTypes.add(parameterType);

        return this;
    }

    public ContractEventFilter build() {
        final ContractEventSpecification spec = new ContractEventSpecification();
        spec.setEventName(eventName);
        spec.setIndexedParameterTypes(indexedParameterTypes);
        spec.setNonIndexedParameterTypes(nonIndexedParameterTypes);

        final ContractEventFilter filter = new ContractEventFilter();
        filter.setContractAddress(contractAddress);
        filter.setEventSpecification(spec);
        filter.setId(id);


        log.info("############## filter contract = {}", filter.getContractAddress());
        
        return filter;
    }
}
