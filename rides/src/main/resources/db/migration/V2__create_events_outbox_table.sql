CREATE TABLE movr_rides.events (
    id UUID PRIMARY KEY,
    ts TIMESTAMP DEFAULT now(),
    event_type STRING NOT NULL,
    event_data JSON NOT NULL
);