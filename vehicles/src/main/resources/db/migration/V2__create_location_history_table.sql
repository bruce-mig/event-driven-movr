CREATE TABLE movr_vehicles.location_history(
    id UUID PRIMARY KEY,
    vehicle_id UUID REFERENCES movr_vehicles.vehicles(id) ON DELETE CASCADE,
    ts TIMESTAMP NOT NULL,
    longitude FLOAT8 NOT NULL,
    latitude FLOAT8 NOT NULL
);
