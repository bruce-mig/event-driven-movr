CREATE TABLE movr_vehicles.vehicles (
    id UUID PRIMARY KEY,
    battery INT8,
    last_ride_start TIMESTAMP,
    last_ride_end TIMESTAMP,
    vehicle_info JSON,
    serial_number INT
        AS ((vehicle_info->'purchase_information'->>'serial_number' )::INT8)
        STORED
);