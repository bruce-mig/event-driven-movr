package com.github.bruce_mig.rides.util;

import com.github.bruce_mig.rides.dto.EndRideRequestDTO;
import com.github.bruce_mig.rides.dto.RideRequestDTO;
import com.github.bruce_mig.rides.entity.Ride;
import com.github.bruce_mig.rides.events.RideEnded;
import com.github.bruce_mig.rides.events.RideStarted;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class TestHelpers {
    private static Random rnd = new Random();

    private static String rndString(String prefix) {
        return prefix + rnd.nextInt(100000);
    }

    public static String createEmail() {
        return rndString("email")+"@fake.com";
    }

    public static UUID createVehicleId() {
        return UUID.randomUUID();
    }

    public static UUID createRideId() {
        return UUID.randomUUID();
    }

    public static LocalDateTime createDateTime() {
        return LocalDateTime.now();
    }

    public static int createBattery() {
        return rnd.nextInt(100);
    }

    public static double createLatitude() {
        return rnd.nextDouble() * 180 - 90;
    }

    public static double createLongitude() {
        return rnd.nextDouble() * 360 - 180;
    }

    public static Ride createRide() {
        Ride ride = new Ride();
        ride.setId(createRideId());
        ride.setUserEmail(createEmail());
        ride.setVehicleId(createVehicleId());
        ride.setStartTime(LocalDateTime.now());
        ride.setEndTime(LocalDateTime.now().plusSeconds(rnd.nextInt(100000)));
        return ride;
    }

    public static RideRequestDTO createRideRequest(Ride ride) {
        RideRequestDTO dto = new RideRequestDTO();
        dto.setEmail(ride.getUserEmail());
        dto.setVehicleId(ride.getVehicleId().toString());
        return dto;
    }

    public static RideRequestDTO createRideRequest() {
        return createRideRequest(createRide());
    }

    public static EndRideRequestDTO createEndRideRequest(Ride ride) {
        EndRideRequestDTO dto = new EndRideRequestDTO();
        dto.setEmail(ride.getUserEmail());
        dto.setVehicleId(ride.getVehicleId().toString());
        dto.setBattery(String.valueOf(createBattery()));
        dto.setLatitude(String.valueOf(createLatitude()));
        dto.setLongitude(String.valueOf(createLongitude()));
        return dto;
    }

    public static EndRideRequestDTO createEndRideRequest() {
        return createEndRideRequest(createRide());
    }

    public static RideStarted createRideStarted() {
        RideStarted event = new RideStarted();
        event.setRideId(createRideId());
        event.setUserEmail(createEmail());
        event.setVehicleId(createVehicleId());
        event.setStartTime(LocalDateTime.now());
        return event;
    }

    public static RideEnded createRideEnded() {
        RideEnded event = new RideEnded();
        event.setRideId(createRideId());
        event.setVehicleId(createVehicleId());
        event.setBattery(createBattery());
        event.setLatitude(createLatitude());
        event.setLongitude(createLongitude());
        event.setEndTime(LocalDateTime.now());
        return event;
    }
}
