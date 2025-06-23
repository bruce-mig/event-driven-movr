package com.github.bruce_mig.vehicles.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaMessage {
    @JsonProperty(value = "after")
    private EventEnvelope message;

    public EventEnvelope getMessage() {
        return message;
    }

    public void setMessage(EventEnvelope message) {
        this.message = message;
    }
}
