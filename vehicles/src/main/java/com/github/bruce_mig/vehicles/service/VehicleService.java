package com.github.bruce_mig.vehicles.service;

import com.github.bruce_mig.vehicles.dto.VehicleInfoDTO;
import com.github.bruce_mig.vehicles.entity.Vehicle;
import com.github.bruce_mig.vehicles.entity.VehicleWithLocation;
import com.github.bruce_mig.vehicles.exception.InvalidVehicleStateException;
import com.github.bruce_mig.vehicles.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 *  Service to handle basic CRUD functions for vehicles
 */
public interface VehicleService {

    Integer MAX_VEHICLES_TO_RETURN = 20;  // default LIMIT when querying

    Vehicle addVehicle(double latitude, double longitude, int batteryLevel, VehicleInfoDTO vehicleInfo);
    void removeVehicle(UUID vehicleId) throws NotFoundException, InvalidVehicleStateException;
    List<VehicleWithLocation> getVehiclesWithLocation(Integer maxRecords);
    Vehicle getVehicle(UUID vehicleId) throws NotFoundException;
    Vehicle checkoutVehicle(UUID vehicleId, LocalDateTime timestamp) throws NotFoundException;
    Vehicle checkinVehicle(UUID vehicleId, double latitude, double longitude, int batteryLevel, LocalDateTime timestamp)
            throws NotFoundException;
}
