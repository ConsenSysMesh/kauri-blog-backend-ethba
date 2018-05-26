package io.kauri.dbt.settings;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventeumSettings {
    @Value("${eventeum.url}")
    private String url;

    @Value("${eventeum.contractAddress}")
    private String contractAddress;

    @Value("${eventeum.rest.route.filter}")
    private String filterRoute;
}
