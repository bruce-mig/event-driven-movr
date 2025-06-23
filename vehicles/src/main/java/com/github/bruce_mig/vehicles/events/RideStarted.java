package com.github.bruce_mig.vehicles.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideStarted {
    public static final String EVENT_TYPE = "RideStarted";

    @JsonProperty("vehicleId")
    private UUID vehicleId;

    @JsonProperty("startTime")
    private LocalDateTime startTime;

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
