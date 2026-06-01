package com.parking.dto.response;

import java.util.List;
import java.util.Map;

public record ParkingLotStatusResponse(
        Long id,
        String name,
        String address,
        long totalFloors,
        long totalSpots,
        long availableSpots,
        long occupiedSpots,
        long activeTickets,
        boolean isFull,
        List<FloorSummaryResponse> floors
) {}
