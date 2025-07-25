package com.github.bruce_mig.rides.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer object to specify start ride details
 */

public class RideRequestDTO {

    private String email;
    @JsonProperty("vehicle_id")
    private String vehicleId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
