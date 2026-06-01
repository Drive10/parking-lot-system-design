package com.parking.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UnparkVehicleRequest(
        @NotBlank(message = "Ticket UID is required")
        String ticketUid
) {}
