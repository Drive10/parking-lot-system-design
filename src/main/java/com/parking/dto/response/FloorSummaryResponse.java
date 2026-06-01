package com.parking.dto.response;

public record FloorSummaryResponse(
        Long id,
        String name,
        int floorNumber,
        long totalSpots,
        long availableSpots,
        long occupiedSpots
) {}
