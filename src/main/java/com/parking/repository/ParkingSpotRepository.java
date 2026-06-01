package com.parking.repository;

import com.parking.entity.ParkingSpotEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {

    List<ParkingSpotEntity> findByFloorIdOrderBySpotIdentifierAsc(Long floorId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ParkingSpotEntity s WHERE s.floor.id = :floorId AND s.isOccupied = false AND s.allowedTypesRaw LIKE %:type% ORDER BY s.id")
    Optional<ParkingSpotEntity> findFirstAvailable(@Param("floorId") Long floorId, @Param("type") String type);

    @Query("SELECT COUNT(s) FROM ParkingSpotEntity s WHERE s.floor.parkingLot.id = :lotId AND s.isOccupied = false")
    long countAvailableByLotId(@Param("lotId") Long lotId);

    @Query("SELECT COUNT(s) FROM ParkingSpotEntity s WHERE s.floor.parkingLot.id = :lotId")
    long countTotalByLotId(@Param("lotId") Long lotId);

    @Query("SELECT s FROM ParkingSpotEntity s WHERE s.floor.parkingLot.id = :lotId AND s.isOccupied = false AND s.allowedTypesRaw LIKE %:type% ORDER BY s.floor.floorNumber, s.id")
    List<ParkingSpotEntity> findAllAvailableByLotIdAndType(@Param("lotId") Long lotId, @Param("type") String type);
}
