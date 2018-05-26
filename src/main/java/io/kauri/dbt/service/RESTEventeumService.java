package io.kauri.dbt.service;

import io.kauri.dbt.model.dto.eventeum.ContractEventFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class RESTEventeumService implements EventeumService {

    private EventeumRESTApi api;

    @Autowired
    public RESTEventeumService(EventeumRESTApi api) {
        this.api = api;
    }

    @Override
    public void addEventFilter(ContractEventFilter eventFilter) {
        api.addEventFilter(eventFilter);
    }

    @FeignClient(name="eventeum", url="${eventeum.url}")
    public interface EventeumRESTApi {

        @RequestMapping(method = RequestMethod.POST, value="${eventeum.rest.route.filter}")
        AddEventFilterResponse addEventFilter(@RequestBody ContractEventFilter filter);
    }

    public class AddEventFilterResponse {

        private String id;
    }
}
