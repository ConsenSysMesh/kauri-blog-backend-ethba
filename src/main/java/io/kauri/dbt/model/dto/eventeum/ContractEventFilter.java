package io.kauri.dbt.model.dto.eventeum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ContractEventFilter {

    private String id;

    private String contractAddress;

    private ContractEventSpecification eventSpecification;
}