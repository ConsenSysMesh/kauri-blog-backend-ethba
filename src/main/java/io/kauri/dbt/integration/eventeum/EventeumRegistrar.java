package io.kauri.dbt.integration.eventeum;

import io.kauri.dbt.model.dto.eventeum.ParameterType;
import io.kauri.dbt.service.EventeumService;
import io.kauri.dbt.settings.EventeumSettings;
import io.kauri.dbt.utils.ContractEventFilterBuilder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventeumRegistrar implements InitializingBean {

    private EventeumService eventeumService;

    private EventeumSettings eventeumSettings;

    @Autowired
    public EventeumRegistrar(EventeumService eventeumService, EventeumSettings eventeumSettings) {
        this.eventeumService = eventeumService;
        this.eventeumSettings = eventeumSettings;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Hard coded events
        final ContractEventFilterBuilder postSubmitted = new ContractEventFilterBuilder();
        postSubmitted
                .id("PostSubmitted")
                .eventName("PostSubmitted")
                .contractAddress(getContractAddress())
                .indexedParameter(ParameterType.BYTES32)
                .indexedParameter(ParameterType.ADDRESS)
                .nonIndexedParameter(ParameterType.BYTES32);

        eventeumService.addEventFilter(postSubmitted.build());

        final ContractEventFilterBuilder postTipped = new ContractEventFilterBuilder();
        postTipped
                .id("PostTipped")
                .eventName("PostTipped")
                .contractAddress(getContractAddress())
                .indexedParameter(ParameterType.BYTES32)
                .indexedParameter(ParameterType.ADDRESS)
                .nonIndexedParameter(ParameterType.UINT256);

        eventeumService.addEventFilter(postTipped.build());

        final ContractEventFilterBuilder blogTipped = new ContractEventFilterBuilder();
        blogTipped
                .id("BlogTipped")
                .eventName("BlogTipped")
                .contractAddress(getContractAddress())
                .indexedParameter(ParameterType.ADDRESS)
                .indexedParameter(ParameterType.ADDRESS)
                .nonIndexedParameter(ParameterType.UINT256);

        eventeumService.addEventFilter(blogTipped.build());
    }

    private String getContractAddress() {
        log.info("############## eventeumSettings.getContractAddress() = {}", eventeumSettings.getContractAddress());
        return eventeumSettings.getContractAddress();
    }
}
