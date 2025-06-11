package com.github.bruce_mig.rides.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.github.bruce_mig.rides.entity.Ride;
import com.github.bruce_mig.rides.exception.InvalidVehicleStateException;
import com.github.bruce_mig.rides.exception.NotFoundException;


public interface RideService {

    Ride startRide(UUID vehicleId, String userEmail, LocalDateTime startTime)
            throws InvalidVehicleStateException;
    Ride endRide(UUID vehicleId,
                 String userEmail,
                 int battery,
                 double latitude,
                 double longitude,
                 LocalDateTime endTime) throws NotFoundException;
    List<Ride> getRidesForUser(String userEmail);
    Ride getActiveRide(UUID vehicleId, String userEmail) throws NotFoundException;

}
