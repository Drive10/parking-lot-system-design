package com.parking.service;

import com.parking.constants.VehicleType;
import com.parking.entity.VehicleEntity;
import com.parking.exception.VehicleAlreadyExistsException;
import com.parking.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public VehicleEntity getOrCreateVehicle(String registrationNumber, String color, VehicleType type) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber)
                .orElseGet(() -> {
                    VehicleEntity vehicle = VehicleEntity.builder()
                            .registrationNumber(registrationNumber)
                            .color(color)
                            .vehicleType(type)
                            .build();
                    return vehicleRepository.save(vehicle);
                });
    }
}
