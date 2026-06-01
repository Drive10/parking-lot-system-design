package com.parking.strategy;

import com.parking.constants.VehicleType;
import com.parking.entity.ParkingSpotEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Primary
@Component
public class NearestFirstStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpotEntity> findSpot(List<ParkingSpotEntity> availableSpots, VehicleType vehicleType) {
        return availableSpots.stream()
                .filter(s -> s.canPark(vehicleType))
                .findFirst();
    }
}
