package io.kauri.dbt.service;

import io.kauri.dbt.model.dto.eventeum.ContractEventFilter;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class RESTEventeumService implements EventeumService {

    @Override
    public void addEventFilter(ContractEventFilter eventFilter) {

    }

//    @FeignClient(name="eventeum", url="${eventeum.url}")
//    public interface EventeumRESTApi {
//
//        @RequestMapping(method = RequestMethod.GET, value="${auth.rest.route.user}/{userId}")
//        User getUser(@PathVariable(name="userId") String userId);
//
//        @RequestMapping(method = RequestMethod.GET, value="${auth.rest.route.moderator}/{category}")
//        List<User> getModeratorUsers(@PathVariable(name="category") String category);
//    }
}
