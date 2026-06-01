package com.parking.repository;

import com.parking.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByRegistrationNumber(String registrationNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
}
