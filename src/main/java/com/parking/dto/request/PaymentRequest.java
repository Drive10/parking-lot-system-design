package com.parking.dto.request;

import com.parking.constants.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotBlank(message = "Ticket UID is required")
        String ticketUid,

        @NotNull(message = "Payment method is required")
        PaymentMethod method
) {}
