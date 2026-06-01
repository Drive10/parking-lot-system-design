package com.parking.entity;

import com.parking.constants.ParkingSpotType;
import com.parking.constants.VehicleType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "parking_spots")
public class ParkingSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spot_identifier", nullable = false, length = 20)
    private String spotIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private ParkingFloorEntity floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "spot_type", nullable = false, length = 20)
    private ParkingSpotType spotType;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "allowed_types", nullable = false, length = 100)
    private String allowedTypesRaw;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ParkingSpotEntity() {
    }

    public ParkingSpotEntity(Long id, String spotIdentifier, ParkingFloorEntity floor, ParkingSpotType spotType, boolean isOccupied, String allowedTypesRaw, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.spotIdentifier = spotIdentifier;
        this.floor = floor;
        this.spotType = spotType;
        this.isOccupied = isOccupied;
        this.allowedTypesRaw = allowedTypesRaw;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpotIdentifier() {
        return spotIdentifier;
    }

    public void setSpotIdentifier(String spotIdentifier) {
        this.spotIdentifier = spotIdentifier;
    }

    public ParkingFloorEntity getFloor() {
        return floor;
    }

    public void setFloor(ParkingFloorEntity floor) {
        this.floor = floor;
    }

    public ParkingSpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(ParkingSpotType spotType) {
        this.spotType = spotType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getAllowedTypesRaw() {
        return allowedTypesRaw;
    }

    public void setAllowedTypesRaw(String allowedTypesRaw) {
        this.allowedTypesRaw = allowedTypesRaw;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Set<VehicleType> getAllowedTypes() {
        return Set.of(allowedTypesRaw.split(","))
                .stream().map(String::trim)
                .map(VehicleType::valueOf)
                .collect(Collectors.toSet());
    }

    public void setAllowedTypes(Set<VehicleType> types) {
        this.allowedTypesRaw = String.join(",", types.stream().map(Enum::name).toList());
    }

    public boolean canPark(VehicleType type) {
        return !isOccupied && getAllowedTypes().contains(type);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String spotIdentifier;
        private ParkingFloorEntity floor;
        private ParkingSpotType spotType;
        private boolean isOccupied;
        private String allowedTypesRaw;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder spotIdentifier(String spotIdentifier) {
            this.spotIdentifier = spotIdentifier;
            return this;
        }

        public Builder floor(ParkingFloorEntity floor) {
            this.floor = floor;
            return this;
        }

        public Builder spotType(ParkingSpotType spotType) {
            this.spotType = spotType;
            return this;
        }

        public Builder isOccupied(boolean isOccupied) {
            this.isOccupied = isOccupied;
            return this;
        }

        public Builder allowedTypesRaw(String allowedTypesRaw) {
            this.allowedTypesRaw = allowedTypesRaw;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ParkingSpotEntity build() {
            return new ParkingSpotEntity(id, spotIdentifier, floor, spotType, isOccupied, allowedTypesRaw, createdAt, updatedAt);
        }
    }
}
