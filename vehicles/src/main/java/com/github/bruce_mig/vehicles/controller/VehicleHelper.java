package com.github.bruce_mig.vehicles.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bruce_mig.vehicles.dto.LocationDetailsDTO;
import com.github.bruce_mig.vehicles.dto.VehicleWithHistoryDTO;
import com.github.bruce_mig.vehicles.dto.VehicleWithLocationDTO;
import com.github.bruce_mig.vehicles.entity.LocationHistory;
import com.github.bruce_mig.vehicles.entity.Vehicle;
import com.github.bruce_mig.vehicles.entity.VehicleWithLocation;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;

/**
 * Helper class to translate Vehicles to Data Transfer Objects.
 * (This is in a separate class because both the VehicleController and the RideController need it.)
 */

public final class VehicleHelper {

    private static final ModelMapper modelMapper = new ModelMapper();

    // this class just contains static methods so don't allow it to be created
    private VehicleHelper() {
    }

    /**
     * Converts a list of Vehicle entity objects to a list of VehicleWithLocationDTO.
     *
     * @param vehicleList   list of Vehicle objects
     * @return              List of RideWithVehicleDTOs
     */
    public static final List<VehicleWithLocationDTO> toWithLocationList(List<Vehicle> vehicleList) {
        return vehicleList.stream().map(v -> {
                    try {
                        return toWithLocationDTO(v);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
            .collect(Collectors.toList());
    }

    /**
     * Converts a list of VehicleWithLocation entity objects to a list of VehicleWithLocationDTO objects.
     * @param vehicleWithLocationList   List of VehicleWithLocation objects
     * @return                          List of VehicleWithLocationDTO's
     */
    public static final List<VehicleWithLocationDTO> toVehicleWithLocationDTOList(List<VehicleWithLocation> vehicleWithLocationList) {
        return vehicleWithLocationList.stream().map(v -> {
            try {
                return toVehicleWithLocationDTO(v);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    /**
     * Converts the Vehicle entity object to a VehicleWithLocationDTO.
     *
     * @param vehicle   the Vehicle entity object
     * @return          VehicleWithLocationDTO
     */

    public static final VehicleWithLocationDTO toWithLocationDTO(Vehicle vehicle) throws IOException {
        VehicleWithLocationDTO vehicleWithLocationDTO = modelMapper.map(vehicle, VehicleWithLocationDTO.class);
//        vehicleWithLocationDTO.setVehicleInfo(new JSONObject(vehicle.getVehicleInfo()).toMap());
        vehicleWithLocationDTO.setVehicleInfo(parseJsonToMap(vehicle.getVehicleInfo()));

        // get the location history info (already sorted, 
        // descending by timestamp)
        if (!vehicle.getLocationHistoryList().isEmpty()) {
            LocationHistory locationHistory = vehicle.getLocationHistoryList().get(0);
            vehicleWithLocationDTO.setTimestamp(locationHistory.getTimestamp());
            vehicleWithLocationDTO.setLastLatitude(locationHistory.getLatitude());
            vehicleWithLocationDTO.setLastLongitude(locationHistory.getLongitude());
        }
        return vehicleWithLocationDTO;
    }

    /**
     * Converts the VehicleWithLocation entity object to a VehicleWithLocationDTO.
     *
     * @param vehicleWithLocation   the Vehicle entity object
     * @return          VehicleWithLocationDTO
     */
    public static final VehicleWithLocationDTO toVehicleWithLocationDTO(VehicleWithLocation vehicleWithLocation) throws IOException {
        VehicleWithLocationDTO vehicleWithLocationDTO = modelMapper.map(vehicleWithLocation, VehicleWithLocationDTO.class);
//        vehicleWithLocationDTO.setVehicleInfo(new JSONObject(vehicleWithLocation.getVehicleInfo()).toMap());
        vehicleWithLocationDTO.setVehicleInfo(parseJsonToMap(vehicleWithLocation.getVehicleInfo()));

        return vehicleWithLocationDTO;
    }

    /**
     * Converts the Vehicle entity object to a VehicleWithHistoryDTO.
     *
     * @param vehicle   the Vehicle entity object
     * @return          VehicleWithHistoryDTO
     */
    public static final VehicleWithHistoryDTO toWithHistoryDTO(Vehicle vehicle) {
        VehicleWithHistoryDTO vehicleWithLocationDTO = modelMapper.map(vehicle, VehicleWithHistoryDTO.class);
//        vehicleWithLocationDTO.setVehicleInfo(new JSONObject(vehicle.getVehicleInfo()).toMap());
        vehicleWithLocationDTO.setVehicleInfo(parseJsonToMap(vehicle.getVehicleInfo()));

        vehicleWithLocationDTO.setLocationDetailsDTOList(
            vehicle.getLocationHistoryList().stream().map(lh -> {
                return modelMapper.map(lh, LocationDetailsDTO.class);
            })
                .collect(Collectors.toList()));
        return vehicleWithLocationDTO;
    }

    @SneakyThrows
    public static Map<String, Object> parseJsonToMap(String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, Map.class);
    }
}
