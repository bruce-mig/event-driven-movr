package com.github.bruce_mig.vehicles.entity;

import org.junit.jupiter.api.Test;

import static com.github.bruce_mig.vehicles.util.TestHelpers.createTimestamp;
import static com.github.bruce_mig.vehicles.util.TestHelpers.createVehicle;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleTest {

    @Test
    public void getInUse_shouldReturnFalse_ifNoRideTimesRecorded() {
        Vehicle vehicle = createVehicle();
        vehicle.setLastRideStart(null);
        vehicle.setLastRideEnd(null);

        assertFalse(vehicle.getInUse());
    }

    @Test
    public void getInUse_shouldReturnFalse_ifNoStartTimeRecorded() {
        Vehicle vehicle = createVehicle();
        vehicle.setLastRideStart(null);
        vehicle.setLastRideEnd(createTimestamp());

        assertFalse(vehicle.getInUse());
    }

    @Test
    public void getInUse_shouldReturnTrue_ifNoEndTimeRecorded() {
        Vehicle vehicle = createVehicle();
        vehicle.setLastRideStart(createTimestamp());
        vehicle.setLastRideEnd(null);

        assertTrue(vehicle.getInUse());
    }

    @Test
    public void getInUse_shouldReturnTrue_ifStartTimeAfterEndTime() {
        Vehicle vehicle = createVehicle();
        vehicle.setLastRideStart(createTimestamp().plusSeconds(1000));
        vehicle.setLastRideEnd(createTimestamp());

        assertTrue(vehicle.getInUse());
    }

    @Test
    public void getInUse_shouldReturnFalse_ifStartTimeBeforeEndTime() {
        Vehicle vehicle = createVehicle();
        vehicle.setLastRideStart(createTimestamp());
        vehicle.setLastRideEnd(createTimestamp().plusSeconds(1000));

        assertFalse(vehicle.getInUse());
    }
}