package com.github.bruce_mig.rides.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventPublisher {
    private final EventRepository repository;
    private final ObjectMapper mapper;

    public EventPublisher(EventRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void publish(String eventType, Event event){
        Map<String, Object> data = mapper.convertValue(event, Map.class);

        EventEnvelope envelope = new EventEnvelope();
        envelope.setEventType(eventType);
        envelope.setEventData(data);

        repository.save(envelope);
    }

}
