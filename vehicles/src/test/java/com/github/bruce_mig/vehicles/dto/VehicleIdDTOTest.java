package com.github.bruce_mig.vehicles.dto;

import com.github.bruce_mig.vehicles.entity.Vehicle;
import org.junit.jupiter.api.Test;

import static com.github.bruce_mig.vehicles.util.TestHelpers.createVehicle;
import static org.junit.jupiter.api.Assertions.assertEquals;


class VehicleIdDTOTest {
    @Test
    public void fromVehicle_shouldReturnAVehicleIdDTO() {
        Vehicle vehicle = createVehicle();

        VehicleIdDTO dto = VehicleIdDTO.fromVehicle(vehicle);

        assertEquals(vehicle.getId(), dto.getId());
    }
}