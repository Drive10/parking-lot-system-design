package com.parking.repository;

import com.parking.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByPaymentUid(String paymentUid);
}
