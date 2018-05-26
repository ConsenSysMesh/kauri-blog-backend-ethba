package io.kauri.dbt.settings;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class KafkaSettings {

    @Value("${kafka.bootstrap.addresses}")
    private String bootstrapAddresses;

    @Value("${kafka.topic.contractEvents}")
    private String contractEventsTopic;
}
