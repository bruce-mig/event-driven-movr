package com.github.bruce_mig.rides.controller;

import com.github.bruce_mig.rides.dto.EndRideRequestDTO;
import com.github.bruce_mig.rides.dto.RideDTO;
import com.github.bruce_mig.rides.dto.RideRequestDTO;
import com.github.bruce_mig.rides.dto.RideResponseDTO;
import com.github.bruce_mig.rides.entity.Ride;
import com.github.bruce_mig.rides.exception.InvalidUUIDException;
import com.github.bruce_mig.rides.exception.InvalidValueException;
import com.github.bruce_mig.rides.exception.InvalidVehicleStateException;
import com.github.bruce_mig.rides.exception.NotFoundException;
import com.github.bruce_mig.rides.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


import static com.github.bruce_mig.rides.util.Constants.MSG_RIDE_ENDED;
import static com.github.bruce_mig.rides.util.Constants.MSG_RIDE_STARTED;
import static com.github.bruce_mig.rides.util.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RideControllerTest {
    @Mock
    private RideService rideService;

    private RideController rideController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        rideController = new RideController(rideService);
    }

    @Test
    public void getActiveRide_shouldThrowAnException_ifTheVehicleIdIsInvalid() throws NotFoundException {
        String vehicleId = "INVALID";
        String email = createEmail();

        assertThrows(InvalidUUIDException.class, () -> {
            rideController.getActiveRide(vehicleId, email);
        });

        verify(rideService, never()).getActiveRide(any(), any());
    }

    @Test
    public void getActiveRide_shouldThrowAnException_ifTheRideIsNotFound() throws NotFoundException {
        UUID vehicleId = createVehicleId();
        String email = createEmail();

        when(rideService.getActiveRide(vehicleId, email)).thenThrow(new NotFoundException("Not Found"));

        assertThrows(NotFoundException.class, () -> {
            rideController.getActiveRide(vehicleId.toString(), email);
        });
    }

    @Test
    public void getActiveRide_shouldReturnTheRide_ifItExists() throws NotFoundException, InvalidUUIDException {
        Ride ride = createRide();

        when(rideService.getActiveRide(ride.getVehicleId(), ride.getUserEmail())).thenReturn(ride);

        RideDTO dto = rideController.getActiveRide(ride.getVehicleId().toString(), ride.getUserEmail()).getBody();

        assertEquals(ride.getId(), dto.getId());
        assertEquals(ride.getVehicleId(), dto.getVehicleId());
        assertEquals(ride.getUserEmail(), dto.getUserEmail());
        assertEquals(ride.getStartTime(), dto.getStartTime());
        assertEquals(ride.getEndTime(), dto.getEndTime());
    }

    @Test
    public void startRide_shouldThrowAnException_ifTheVehicleIdIsInvalid() throws InvalidVehicleStateException {
        RideRequestDTO request = createRideRequest();
        request.setVehicleId("INVALID");

        assertThrows(InvalidUUIDException.class, () -> {
            rideController.startRide(request);
        });

        verify(rideService, never()).startRide(any(), any(), any());
    }

    @Test
    public void startRide_shouldStartTheRide() throws InvalidUUIDException, InvalidVehicleStateException {
        Ride ride = createRide();
        RideRequestDTO request = createRideRequest(ride);

        when(rideService.startRide(eq(UUID.fromString(request.getVehicleId())), eq(request.getEmail()), any()))
                .thenReturn(ride);

        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        RideResponseDTO response = rideController.startRide(request).getBody();
        LocalDateTime after = LocalDateTime.now(ZoneOffset.UTC);

        ArgumentCaptor<LocalDateTime> tsCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        verify(rideService).startRide(eq(UUID.fromString(request.getVehicleId())), eq(request.getEmail()), tsCaptor.capture());

        assertTrue(
                (tsCaptor.getValue().compareTo(before) >= 0) && (tsCaptor.getValue().compareTo(after) <= 0),
                "Timestamp should be between before and after"
        );

        assertEquals(1, response.getMessages().length);
        assertEquals(String.format(MSG_RIDE_STARTED, request.getVehicleId()), response.getMessages()[0]);

        RideDTO dto = response.getRideDTO();
        assertEquals(ride.getId(), dto.getId());
        assertEquals(ride.getVehicleId(), dto.getVehicleId());
        assertEquals(ride.getUserEmail(), dto.getUserEmail());
        assertEquals(ride.getStartTime(), dto.getStartTime());
        assertEquals(ride.getEndTime(), dto.getEndTime());
    }

    @Test
    public void endRide_shouldThrowAnException_ifTheVehicleIdIsInvalid() throws NotFoundException {
        EndRideRequestDTO request = createEndRideRequest();
        request.setVehicleId("INVALID");

        assertThrows(InvalidUUIDException.class, () -> {
            rideController.endRide(request);
        });

        verify(rideService, never()).endRide(any(), any(), anyInt(), anyDouble(), anyDouble(), any());
    }

    @Test
    public void endRide_shouldThrowAnException_ifTheBatteryIsInvalid() throws NotFoundException {
        EndRideRequestDTO request = createEndRideRequest();
        request.setBattery("INVALID");

        assertThrows(InvalidValueException.class, () -> {
            rideController.endRide(request);
        });

        verify(rideService, never()).endRide(any(), any(), anyInt(), anyDouble(), anyDouble(), any());
    }

    @Test
    public void endRide_shouldThrowAnException_ifTheLatitudeIsInvalid() throws NotFoundException {
        EndRideRequestDTO request = createEndRideRequest();
        request.setLatitude("INVALID");

        assertThrows(InvalidValueException.class, () -> {
            rideController.endRide(request);
        });

        verify(rideService, never()).endRide(any(), any(), anyInt(), anyDouble(), anyDouble(), any());
    }

    @Test
    public void endRide_shouldThrowAnException_ifTheLongitudeIsInvalid() throws NotFoundException {
        EndRideRequestDTO request = createEndRideRequest();
        request.setLongitude("INVALID");

        assertThrows(InvalidValueException.class, () -> {
            rideController.endRide(request);
        });

        verify(rideService, never()).endRide(any(), any(), anyInt(), anyDouble(), anyDouble(), any());
    }

    @Test
    public void endRide_shouldThrowAnException_ifNoActiveRideExists() throws NotFoundException {
        EndRideRequestDTO request = createEndRideRequest();

        when(rideService.endRide(
                eq(UUID.fromString(request.getVehicleId())),
                eq(request.getEmail()),
                eq(Integer.parseInt(request.getBattery())),
                eq(Double.parseDouble(request.getLatitude())),
                eq(Double.parseDouble(request.getLongitude())),
                any())
        ).thenThrow(new NotFoundException("Not Found"));

        assertThrows(NotFoundException.class, () -> {
            rideController.endRide(request);
        });
    }

    @Test
    public void endRide_shouldEndTheRide_ifItExists() throws NotFoundException, InvalidUUIDException, InvalidValueException {
        Ride ride = createRide();
        EndRideRequestDTO request = createEndRideRequest(ride);

        when(rideService.endRide(
                eq(UUID.fromString(request.getVehicleId())),
                eq(request.getEmail()),
                eq(Integer.parseInt(request.getBattery())),
                eq(Double.parseDouble(request.getLatitude())),
                eq(Double.parseDouble(request.getLongitude())),
                any())
        ).thenReturn(ride);

        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        RideResponseDTO response = rideController.endRide(request).getBody();
        LocalDateTime after = LocalDateTime.now(ZoneOffset.UTC);

        ArgumentCaptor<LocalDateTime> tsCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        verify(rideService).endRide(
                eq(UUID.fromString(request.getVehicleId())),
                eq(request.getEmail()),
                eq(Integer.parseInt(request.getBattery())),
                eq(Double.parseDouble(request.getLatitude())),
                eq(Double.parseDouble(request.getLongitude())),
                tsCaptor.capture());

        assertTrue(
                (tsCaptor.getValue().compareTo(before) >= 0) && (tsCaptor.getValue().compareTo(after) <= 0),
                "Timestamp should be between before and after"
        );

        assertEquals(1, response.getMessages().length);
        assertEquals(String.format(MSG_RIDE_ENDED, request.getVehicleId()), response.getMessages()[0]);

        RideDTO dto = response.getRideDTO();
        assertEquals(ride.getId(), dto.getId());
        assertEquals(ride.getVehicleId(), dto.getVehicleId());
        assertEquals(ride.getUserEmail(), dto.getUserEmail());
        assertEquals(ride.getStartTime(), dto.getStartTime());
        assertEquals(ride.getEndTime(), dto.getEndTime());
    }

}