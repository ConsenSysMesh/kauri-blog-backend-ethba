package io.kauri.dbt.message;

import io.kauri.dbt.message.details.EventeumEventDetails;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EventeumMessage extends AbstractMessage<EventeumEventDetails> {

    public static final String TYPE = "CONTRACT_EVENT";

    public EventeumMessage(String type, EventeumEventDetails details) {
        super(type, details);
    }
}
