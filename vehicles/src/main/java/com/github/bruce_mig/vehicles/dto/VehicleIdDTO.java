package com.github.bruce_mig.vehicles.dto;

import com.github.bruce_mig.vehicles.entity.Vehicle;

import java.util.UUID;

public class VehicleIdDTO {
    public static VehicleIdDTO fromVehicle(Vehicle vehicle) {
        VehicleIdDTO dto = new VehicleIdDTO();
        dto.setId(vehicle.getId());
        return dto;
    }

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
