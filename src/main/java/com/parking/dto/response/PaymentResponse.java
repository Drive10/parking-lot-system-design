package com.parking.dto.response;

import com.parking.constants.PaymentMethod;
import com.parking.constants.PaymentStatus;
import java.time.LocalDateTime;

public record PaymentResponse(
        String paymentUid,
        String ticketUid,
        Double amount,
        PaymentMethod method,
        PaymentStatus status,
        LocalDateTime timestamp
) {}
