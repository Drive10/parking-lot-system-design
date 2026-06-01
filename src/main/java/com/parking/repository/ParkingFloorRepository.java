package com.parking.repository;

import com.parking.entity.ParkingFloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloorEntity, Long> {
    List<ParkingFloorEntity> findByParkingLotIdOrderByFloorNumberAsc(Long parkingLotId);
}
