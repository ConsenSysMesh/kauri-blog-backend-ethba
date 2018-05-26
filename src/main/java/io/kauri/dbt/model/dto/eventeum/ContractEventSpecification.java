package io.kauri.dbt.model.dto.eventeum;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class ContractEventSpecification {

    private String eventName;

    private List<ParameterType> indexedParameterTypes;

    private List<ParameterType> nonIndexedParameterTypes;
}

