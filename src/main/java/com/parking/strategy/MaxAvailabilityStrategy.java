package com.parking.strategy;

import com.parking.constants.VehicleType;
import com.parking.entity.ParkingSpotEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class MaxAvailabilityStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpotEntity> findSpot(List<ParkingSpotEntity> availableSpots, VehicleType vehicleType) {
        return availableSpots.stream()
                .filter(s -> s.canPark(vehicleType))
                .min(Comparator.comparingLong(s -> s.getFloor().getId()));
    }
}
