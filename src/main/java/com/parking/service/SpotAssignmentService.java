package com.parking.service;

import com.parking.constants.VehicleType;
import com.parking.entity.ParkingSpotEntity;
import com.parking.exception.ParkingFullException;
import com.parking.repository.ParkingSpotRepository;
import com.parking.strategy.ParkingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SpotAssignmentService {

    private final ParkingSpotRepository spotRepository;
    private final ParkingStrategy parkingStrategy;

    public SpotAssignmentService(ParkingSpotRepository spotRepository, ParkingStrategy parkingStrategy) {
        this.spotRepository = spotRepository;
        this.parkingStrategy = parkingStrategy;
    }

    @Transactional
    public ParkingSpotEntity assignSpot(Long lotId, VehicleType vehicleType) {
        List<ParkingSpotEntity> available = spotRepository.findAllAvailableByLotIdAndType(
                lotId, "%" + vehicleType.name() + "%");

        Optional<ParkingSpotEntity> spotOpt = parkingStrategy.findSpot(available, vehicleType);
        if (spotOpt.isEmpty()) {
            throw new ParkingFullException(
                    "No available spot for vehicle type: " + vehicleType);
        }

        ParkingSpotEntity spot = spotOpt.get();
        spot.setOccupied(true);
        return spotRepository.save(spot);
    }

    @Transactional
    public void releaseSpot(Long spotId) {
        spotRepository.findById(spotId).ifPresent(spot -> {
            spot.setOccupied(false);
            spotRepository.save(spot);
        });
    }
}
