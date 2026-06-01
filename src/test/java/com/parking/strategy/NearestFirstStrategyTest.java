package com.parking.strategy;

import com.parking.constants.VehicleType;
import com.parking.entity.ParkingFloorEntity;
import com.parking.entity.ParkingLotEntity;
import com.parking.entity.ParkingSpotEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NearestFirstStrategyTest {

    private NearestFirstStrategy strategy;
    private ParkingSpotEntity spot1;
    private ParkingSpotEntity spot2;

    @BeforeEach
    void setUp() {
        strategy = new NearestFirstStrategy();

        ParkingLotEntity lot = new ParkingLotEntity();
        lot.setId(1L);

        ParkingFloorEntity floor = new ParkingFloorEntity();
        floor.setId(1L);
        floor.setFloorNumber(0);
        floor.setName("G");
        floor.setParkingLot(lot);

        spot1 = new ParkingSpotEntity();
        spot1.setId(1L);
        spot1.setSpotIdentifier("G-C1");
        spot1.setFloor(floor);
        spot1.setOccupied(false);
        spot1.setAllowedTypesRaw("CAR");

        spot2 = new ParkingSpotEntity();
        spot2.setId(2L);
        spot2.setSpotIdentifier("G-C2");
        spot2.setFloor(floor);
        spot2.setOccupied(false);
        spot2.setAllowedTypesRaw("CAR");
    }

    @Test
    void shouldFindFirstAvailableSpot() {
        List<ParkingSpotEntity> available = List.of(spot1, spot2);
        Optional<ParkingSpotEntity> result = strategy.findSpot(available, VehicleType.CAR);
        assertTrue(result.isPresent());
        assertEquals("G-C1", result.get().getSpotIdentifier());
    }

    @Test
    void shouldSkipOccupiedSpot() {
        spot1.setOccupied(true);
        List<ParkingSpotEntity> available = List.of(spot1, spot2);
        Optional<ParkingSpotEntity> result = strategy.findSpot(available, VehicleType.CAR);
        assertTrue(result.isPresent());
        assertEquals("G-C2", result.get().getSpotIdentifier());
    }

    @Test
    void shouldReturnEmptyWhenNoneAvailable() {
        spot1.setOccupied(true);
        spot2.setOccupied(true);
        List<ParkingSpotEntity> available = List.of(spot1, spot2);
        Optional<ParkingSpotEntity> result = strategy.findSpot(available, VehicleType.CAR);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFilterByVehicleType() {
        spot2.setAllowedTypesRaw("BIKE");
        List<ParkingSpotEntity> available = List.of(spot1, spot2);
        Optional<ParkingSpotEntity> result = strategy.findSpot(available, VehicleType.CAR);
        assertTrue(result.isPresent());
        assertEquals("G-C1", result.get().getSpotIdentifier());
    }
}
