package com.parking.entity;

import com.parking.constants.PaymentMethod;
import com.parking.constants.PaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_uid", nullable = false, unique = true, length = 20)
    private String paymentUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketEntity ticket;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public PaymentEntity() {
    }

    public PaymentEntity(Long id, String paymentUid, TicketEntity ticket, Double amount, PaymentMethod method, PaymentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.paymentUid = paymentUid;
        this.ticket = ticket;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(String paymentUid) {
        this.paymentUid = paymentUid;
    }

    public TicketEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
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
        private String paymentUid;
        private TicketEntity ticket;
        private Double amount;
        private PaymentMethod method;
        private PaymentStatus status;
        private LocalDateTime createdAt;

        Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder paymentUid(String paymentUid) {
            this.paymentUid = paymentUid;
            return this;
        }

        public Builder ticket(TicketEntity ticket) {
            this.ticket = ticket;
            return this;
        }

        public Builder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder method(PaymentMethod method) {
            this.method = method;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PaymentEntity build() {
            return new PaymentEntity(id, paymentUid, ticket, amount, method, status, createdAt);
        }
    }
}
