package com.github.bruce_mig.rides.events;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "events")
public class EventEnvelope {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "event_type")
    private String eventType;
    @Type(JsonBinaryType.class)
    @Column(name = "event_data", columnDefinition = "jsonb")
    private Map<String, Object> eventData;


}
