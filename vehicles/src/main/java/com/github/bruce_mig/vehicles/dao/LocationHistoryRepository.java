package com.github.bruce_mig.vehicles.dao;

import java.util.UUID;

import com.github.bruce_mig.vehicles.entity.LocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA Repository for Location History
 */

@Repository
public interface LocationHistoryRepository extends JpaRepository<LocationHistory, UUID> {

    /**
     * Returns the location history for a specific vehicle, with more recent entries first.
     *
     * Spring JPA converts the name of this method into a SQL statement that does the work for us
     * in this case, it creates a SQL query that fetches all location rows for the specified vehicle,
     * orders them by timestamp descending (which puts newest ones first) and then returns the first
     * one it finds.  This gives us the most recent location history record for the requested vehicle.
     *
     * @param vehicleId The UUID of the vehicle to retrieve history for
     * @return          a single LocationHistory entity
     */
    LocationHistory findFirstByVehicleIdOrderByTimestampDesc(UUID vehicleId);
}
