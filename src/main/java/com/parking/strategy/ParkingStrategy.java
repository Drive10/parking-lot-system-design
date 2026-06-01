package com.parking.strategy;

import com.parking.constants.VehicleType;
import com.parking.entity.ParkingSpotEntity;

import java.util.List;
import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpotEntity> findSpot(List<ParkingSpotEntity> availableSpots, VehicleType vehicleType);
}
