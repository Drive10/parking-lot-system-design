package com.parking.dto.response;

import com.parking.constants.TicketStatus;
import com.parking.constants.VehicleType;
import java.time.LocalDateTime;

public record TicketResponse(
        String ticketUid,
        String registrationNumber,
        String vehicleColor,
        VehicleType vehicleType,
        String spotIdentifier,
        int floorNumber,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        Double fee,
        Boolean isPaid,
        TicketStatus status
) {}
