# event-driven-movr

## Overview

This repository implements a comprehensive **Vehicle Management Service** using a microservices architecture. The system is designed to efficiently handle vehicle, ride, and user management, providing robust event-driven capabilities through the use of **CockroachDB** and **Apache Kafka** (with KRaft consensus algorithm).

### Key Features

- **Microservices-based Architecture**: Modularized services for scalability and independent deployments.
- **Transactional Outbox Pattern**: Reliable event publishing using Change Data Capture (CDC) feature of CockroachDB.
- **Event-Driven Communication**: Services communicate via events published to Apache Kafka, ensuring loose coupling.
- **CQRS and Saga-friendly**: Built to support advanced distributed transaction patterns.
- **UI Gateway**: API gateway and frontend entrypoint for all user interactions.

---

## Architecture

### Services

- **users**: Manages user profiles, authentication, and authorization.
- **vehicles**: Handles vehicle registration, status, and availability.
- **rides**: Manages ride bookings, ride state, and assignments.
- **ui_gateway**: Serves as the external interface (API Gateway), aggregating and routing requests to appropriate backend services.

All services interact with a shared **CockroachDB** cluster, and utilize the **transactional outbox pattern** for event publishing.

### Event Flow

1. **Write-side Services** (e.g., rides, vehicles, users) persist changes to CockroachDB.
2. Each service writes domain events to its own **outbox table** within the same transaction.
3. CDC streams capture new outbox entries, and a connector publishes them as events to **Apache Kafka** topics.
4. **Other services** subscribe to relevant Kafka topics for eventual consistency or workflow triggers.

**Diagram** (conceptual):

```
+-----------+     +---------------+     +------------------+     +------------+
|  ui_gateway|<-->|   [services]  |<--->|  CockroachDB     |<--->| Kafka      |
+-----------+     +---------------+     +------------------+     +------------+
                      ^  | Write/Read         | CDC Streams           ^
                      |  +----+               +----> Outbox           |
                      |                                               |
                      +-----------------------------------------------+
```

---

## Technologies Used

- **Java** (primary language for all backend services)
- **CockroachDB** (distributed SQL database, supports CDC)
- **Apache Kafka** (distributed event streaming)
- **Spring Boot** (recommended for service scaffolding)
- **Docker Compose/Kubernetes** (deployment, optional)

---

## Getting Started

### Prerequisites

- Java 17+ (and Maven or Gradle)
- Docker (for CockroachDB & Kafka)
- [Optional] Kubernetes (for production deployments)

### Running Locally

1. **Clone the repository:**
   ```sh
   git clone https://github.com/bruce-mig/event-driven-movr.git
   cd event-driven-movr
   ```

2. **Start Infrastructure (CockroachDB, Kafka):**  
      Ensure Docker is running on your system.
    - Use included `docker-compose.yml`.
   ```sh
   docker-compose up -d
   ```

3. **Start Services:**
    - For each service (`users`, `vehicles`, `rides`, `ui_gateway`):
      ```sh
      cd <service-name>
      ./mvnw spring-boot:run
      ```

4. **Access the UI Gateway:**
    - Visit [http://localhost:36257](http://localhost:36257) (default port, see config for overrides).

---

## Event Publishing: Transactional Outbox & CDC

- Each microservice writes both its domain data and an "outbox" event in a single transaction.
- CockroachDB's **Change Data Capture (CDC)** streams monitor the outbox tables.
- A connector (e.g., Debezium, native CDC) picks up outbox entries and publishes to the corresponding Kafka topic.
- This ensures strong consistency between changes in the database and events published to Kafka.

**Example Outbox Table Schema:**

| id | aggregate_type | aggregate_id | event_type | payload | created_at |
|----|---------------|--------------|------------|---------|------------|
|... |   "ride"      |   "ride-42"  | "Created"  | {...}   | ...        |

---

## Directory Structure

```
event-driven-movr/
├── rides/              # Ride management service
├── vehicles/           # Vehicle management service
├── users/              # User management and auth service
├── ui_gateway/         # API Gateway and UI
├── data/               # Infrastructure (sql scripts)
├── init-scripts/       # Shell scripts 
├── docker-compose.yaml # Docker compose file 
└── README.md
```

---

[//]: # (## Service APIs)

[//]: # ()
[//]: # (Each service exposes REST APIs documented via [Swagger/OpenAPI]&#40;http://localhost:8080/swagger-ui.html&#41;.)

[//]: # ()
[//]: # (- **users**: `/api/users/*`)

[//]: # (- **vehicles**: `/api/vehicles/*`)

[//]: # (- **rides**: `/api/rides/*`)

[//]: # (- **ui_gateway**: `/api/*` &#40;aggregated endpoints&#41;)

[//]: # ()
[//]: # (---)

## Configuration

- **Database Connection**: Each service reads DB config from `application.yml`.
- **Kafka Topics**: Outbox event types are mapped to topics, configured per service.

[//]: # (- **CDC Connector**: See `/infra` for CDC → Kafka connector configuration.)

---

## Deployment

- **Local Dev**: Use Docker Compose for Kafka and CockroachDB.
- **Production**: Deploy services and infra on Kubernetes or cloud provider.
- **Scaling**: Each service can be scaled independently.

---

## Contribution

1. Fork and clone the repo
2. Create a feature branch: `git checkout -b ft/my-feature`
3. Commit your changes and push: `git push origin ft/my-feature`
4. Open a Pull Request

---

## License

[MIT License](LICENSE)

---

## Authors

- [@bruce-mig](https://github.com/bruce-mig)

---
