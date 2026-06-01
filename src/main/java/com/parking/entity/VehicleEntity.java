package com.parking.entity;

import com.parking.constants.VehicleType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", nullable = false, unique = true, length = 50)
    private String registrationNumber;

    @Column(nullable = false, length = 50)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 20)
    private VehicleType vehicleType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public VehicleEntity() {
    }

    public VehicleEntity(Long id, String registrationNumber, String color, VehicleType vehicleType, LocalDateTime createdAt) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.vehicleType = vehicleType;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String registrationNumber;
        private String color;
        private VehicleType vehicleType;
        private LocalDateTime createdAt;

        Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder vehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public VehicleEntity build() {
            return new VehicleEntity(id, registrationNumber, color, vehicleType, createdAt);
        }
    }
}
