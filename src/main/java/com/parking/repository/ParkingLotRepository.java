package com.parking.repository;

import com.parking.entity.ParkingLotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {
}
