package io.kauri.dbt.integration.eventeum;

import io.kauri.dbt.model.dto.eventeum.ParameterType;
import io.kauri.dbt.service.EventeumService;
import io.kauri.dbt.utils.ContractEventFilterBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventeumRegistrar implements InitializingBean {

    private EventeumService eventeumService;

    @Autowired
    public EventeumRegistrar(EventeumService eventeumService) {
        this.eventeumService = eventeumService;
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
        return "0x4aecf261541f168bb3ca65fa8ff5012498aac3b8";
    }
}
