package com.github.bruce_mig.vehicles.util;


import com.github.bruce_mig.vehicles.dto.NewVehicleDTO;
import com.github.bruce_mig.vehicles.dto.VehicleInfoDTO;
import com.github.bruce_mig.vehicles.entity.LocationHistory;
import com.github.bruce_mig.vehicles.entity.Vehicle;
import com.github.bruce_mig.vehicles.entity.VehicleWithLocation;
import com.github.bruce_mig.vehicles.events.EventEnvelope;
import com.github.bruce_mig.vehicles.events.KafkaMessage;
import com.github.bruce_mig.vehicles.events.RideEnded;
import com.github.bruce_mig.vehicles.events.RideStarted;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestHelpers {
    private static Random rnd = new Random();

    private static String nextString(String prefix) {
        return prefix + rnd.nextInt(10000);
    }

    public static LocalDateTime createTimestamp() {
        return LocalDateTime.now();
    }

    public static Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(createVehicleId());
        vehicle.setSerialNumber(rnd.nextInt(10000));
        vehicle.setBattery(rnd.nextInt(100));
        vehicle.setLastRideStart(createTimestamp());
        vehicle.setLastRideEnd(createTimestamp().plusSeconds(1000));
        vehicle.setVehicleInfo(createVehicleInfoDTO().getAsJsonString());

        List<LocationHistory> locations = IntStream.range(0, rnd.nextInt(5) + 1)
                .mapToObj(i -> createLocationHistory(vehicle))
                .collect(Collectors.toList());

        vehicle.setLocationHistoryList(locations);

        return vehicle;
    }

    public static VehicleWithLocation createVehicleWithLocation() {
        VehicleWithLocation vehicle = new VehicleWithLocation();
        vehicle.setId(createVehicleId());
        vehicle.setSerialNumber(String.valueOf(rnd.nextInt(10000)));
        vehicle.setBattery(rnd.nextInt(100));
        vehicle.setLastRideStart(createTimestamp());
        vehicle.setLastRideEnd(createTimestamp().plusSeconds(1000));
        vehicle.setVehicleInfo(createVehicleInfoDTO().getAsJsonString());
        return vehicle;
    }

    public static LocationHistory createLocationHistory(Vehicle vehicle) {
        LocationHistory locationHistory = new LocationHistory();
        locationHistory.setId(createLocationHistoryId());
        locationHistory.setLatitude(rnd.nextDouble() * 360 - 180);
        locationHistory.setLongitude(rnd.nextDouble() * 180 - 90);
        locationHistory.setTimestamp(createTimestamp().plusSeconds(1000));
        locationHistory.setVehicle(vehicle);
        return locationHistory;
    }

    public static VehicleInfoDTO createVehicleInfoDTO() {
        Map<String, Object> vehicleInfo = new HashMap<>();
        vehicleInfo.put("type", nextString("Type"));
        VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO(vehicleInfo);
        return vehicleInfoDTO;
    }

    public static NewVehicleDTO createNewVehicleDTO() {
        NewVehicleDTO newVehicleDTO = new NewVehicleDTO();
        newVehicleDTO.setBattery(String.valueOf(rnd.nextInt(100)));
        newVehicleDTO.setColor(nextString("Color"));
        newVehicleDTO.setVehicleType(nextString("Type"));
        newVehicleDTO.setId(UUID.randomUUID());
        newVehicleDTO.setManufacturer(nextString("Manufacturer"));
        newVehicleDTO.setPurchaseDate(nextString("Purchase Date"));
        newVehicleDTO.setSerialNumber(String.valueOf(rnd.nextInt(1000000)));
        newVehicleDTO.setLatitude(String.valueOf(rnd.nextDouble() * 180 - 90));
        newVehicleDTO.setLongitude(String.valueOf(rnd.nextDouble() * 360 - 180));
        newVehicleDTO.setWear(nextString("Wear"));
        return newVehicleDTO;
    }

    public static UUID createVehicleId() {
        return UUID.randomUUID();
    }

    public static UUID createLocationHistoryId() { return UUID.randomUUID(); }

    public static KafkaMessage createKafkaMessage() {
        KafkaMessage msg = new KafkaMessage();
        msg.setMessage(createEventEnvelope());
        return msg;
    }

    public static EventEnvelope createEventEnvelope() {
        EventEnvelope envelope = new EventEnvelope();
        envelope.setEventType(nextString("EventType"));
        return envelope;
    }

    public static RideStarted createRideStarted() {
        RideStarted rideStarted = new RideStarted();
        rideStarted.setVehicleId(createVehicleId());
        rideStarted.setStartTime(LocalDateTime.now());
        return rideStarted;
    }

    public static RideEnded createRideEnded() {
        RideEnded rideEnded = new RideEnded();
        rideEnded.setBattery(rnd.nextInt(100));
        rideEnded.setLatitude(rnd.nextDouble() * 180 - 90);
        rideEnded.setLongitude(rnd.nextDouble() * 360 - 180);
        rideEnded.setEndTime(LocalDateTime.now());
        return rideEnded;
    }
}
