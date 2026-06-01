package com.parking.entity;

import com.parking.constants.TicketStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_uid", nullable = false, unique = true, length = 20)
    private String ticketUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", nullable = false)
    private ParkingSpotEntity spot;

    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double fee;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public TicketEntity() {
    }

    public TicketEntity(Long id, String ticketUid, VehicleEntity vehicle, ParkingSpotEntity spot, LocalDateTime entryTime, LocalDateTime exitTime, Double fee, Boolean isPaid, TicketStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ticketUid = ticketUid;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
        this.isPaid = isPaid;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketUid() {
        return ticketUid;
    }

    public void setTicketUid(String ticketUid) {
        this.ticketUid = ticketUid;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpotEntity getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpotEntity spot) {
        this.spot = spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
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
        private String ticketUid;
        private VehicleEntity vehicle;
        private ParkingSpotEntity spot;
        private LocalDateTime entryTime;
        private LocalDateTime exitTime;
        private Double fee;
        private Boolean isPaid;
        private TicketStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder ticketUid(String ticketUid) {
            this.ticketUid = ticketUid;
            return this;
        }

        public Builder vehicle(VehicleEntity vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder spot(ParkingSpotEntity spot) {
            this.spot = spot;
            return this;
        }

        public Builder entryTime(LocalDateTime entryTime) {
            this.entryTime = entryTime;
            return this;
        }

        public Builder exitTime(LocalDateTime exitTime) {
            this.exitTime = exitTime;
            return this;
        }

        public Builder fee(Double fee) {
            this.fee = fee;
            return this;
        }

        public Builder isPaid(Boolean isPaid) {
            this.isPaid = isPaid;
            return this;
        }

        public Builder status(TicketStatus status) {
            this.status = status;
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

        public TicketEntity build() {
            return new TicketEntity(id, ticketUid, vehicle, spot, entryTime, exitTime, fee, isPaid, status, createdAt, updatedAt);
        }
    }
}
