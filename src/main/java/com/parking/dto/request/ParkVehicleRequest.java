package com.parking.dto.request;

import com.parking.constants.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParkVehicleRequest(
        @NotBlank(message = "Registration number is required")
        String registrationNumber,

        @NotBlank(message = "Color is required")
        String color,

        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType
) {}
