CREATE TABLE parking_lots (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    address     VARCHAR(500) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_floors (
    id              BIGSERIAL PRIMARY KEY,
    parking_lot_id  BIGINT NOT NULL REFERENCES parking_lots(id),
    floor_number    INTEGER NOT NULL,
    name            VARCHAR(50) NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (parking_lot_id, floor_number)
);

CREATE TABLE parking_spots (
    id              BIGSERIAL PRIMARY KEY,
    spot_identifier VARCHAR(20) NOT NULL,
    floor_id        BIGINT NOT NULL REFERENCES parking_floors(id),
    spot_type       VARCHAR(20) NOT NULL,
    is_occupied     BOOLEAN NOT NULL DEFAULT FALSE,
    allowed_types   VARCHAR(100) NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (floor_id, spot_identifier)
);

CREATE TABLE vehicles (
    id                  BIGSERIAL PRIMARY KEY,
    registration_number VARCHAR(50) NOT NULL UNIQUE,
    color               VARCHAR(50) NOT NULL,
    vehicle_type        VARCHAR(20) NOT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tickets (
    id              BIGSERIAL PRIMARY KEY,
    ticket_uid      VARCHAR(20) NOT NULL UNIQUE,
    vehicle_id      BIGINT NOT NULL REFERENCES vehicles(id),
    spot_id         BIGINT NOT NULL REFERENCES parking_spots(id),
    entry_time      TIMESTAMP NOT NULL,
    exit_time       TIMESTAMP,
    fee             DECIMAL(10,2) NOT NULL DEFAULT 0,
    is_paid         BOOLEAN NOT NULL DEFAULT FALSE,
    status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
    id              BIGSERIAL PRIMARY KEY,
    payment_uid     VARCHAR(20) NOT NULL UNIQUE,
    ticket_id       BIGINT NOT NULL REFERENCES tickets(id),
    amount          DECIMAL(10,2) NOT NULL,
    method          VARCHAR(20) NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tickets_ticket_uid ON tickets(ticket_uid);
CREATE INDEX idx_tickets_status ON tickets(status);
CREATE INDEX idx_parking_spots_floor_id ON parking_spots(floor_id);
CREATE INDEX idx_parking_spots_occupied ON parking_spots(is_occupied);
CREATE INDEX idx_vehicles_registration ON vehicles(registration_number);
