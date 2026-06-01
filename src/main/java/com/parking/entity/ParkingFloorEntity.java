package com.parking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_floors")
public class ParkingFloorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLotEntity parkingLot;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ParkingFloorEntity() {
    }

    public ParkingFloorEntity(Long id, ParkingLotEntity parkingLot, Integer floorNumber, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.parkingLot = parkingLot;
        this.floorNumber = floorNumber;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingLotEntity getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLotEntity parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private ParkingLotEntity parkingLot;
        private Integer floorNumber;
        private String name;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder parkingLot(ParkingLotEntity parkingLot) {
            this.parkingLot = parkingLot;
            return this;
        }

        public Builder floorNumber(Integer floorNumber) {
            this.floorNumber = floorNumber;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
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

        public ParkingFloorEntity build() {
            return new ParkingFloorEntity(id, parkingLot, floorNumber, name, createdAt, updatedAt);
        }
    }
}
