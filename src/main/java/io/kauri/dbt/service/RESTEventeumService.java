package io.kauri.dbt.service;

import io.kauri.dbt.model.dto.eventeum.ContractEventFilter;
import io.kauri.dbt.settings.EventeumSettings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class RESTEventeumService implements EventeumService, InitializingBean {

    private EventeumRESTApi api;

    @Autowired
    private EventeumSettings settings;

    @Autowired
    public RESTEventeumService(EventeumRESTApi api) {
        this.api = api;
    }

    @Override
    public void addEventFilter(ContractEventFilter eventFilter) {
        api.addEventFilter(eventFilter);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("*********** " + settings.getUrl());
    }

    @FeignClient(name="eventeum", url="#{eventeumSettings.url}")
    public interface EventeumRESTApi {

        @RequestMapping(method = RequestMethod.POST,
                value="event-filter", consumes="application/json", produces="application/json")
        AddEventFilterResponse addEventFilter(@RequestBody ContractEventFilter filter);
    }

    public class AddEventFilterResponse {

        private String id;
    }
}
