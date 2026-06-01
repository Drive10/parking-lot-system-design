package com.parking.config;

import com.parking.constants.ParkingSpotType;
import com.parking.constants.VehicleType;
import com.parking.entity.ParkingFloorEntity;
import com.parking.entity.ParkingLotEntity;
import com.parking.entity.ParkingSpotEntity;
import com.parking.repository.ParkingFloorRepository;
import com.parking.repository.ParkingLotRepository;
import com.parking.repository.ParkingSpotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final ParkingLotRepository lotRepository;
    private final ParkingFloorRepository floorRepository;
    private final ParkingSpotRepository spotRepository;

    public DataInitializer(ParkingLotRepository lotRepository, ParkingFloorRepository floorRepository, ParkingSpotRepository spotRepository) {
        this.lotRepository = lotRepository;
        this.floorRepository = floorRepository;
        this.spotRepository = spotRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (lotRepository.count() > 0) {
            log.info("Database already initialized, skipping data seed");
            return;
        }

        log.info("Initializing parking lot data...");

        ParkingLotEntity lot = ParkingLotEntity.builder()
                .name("Central Parking Garage")
                .address("123 Main Street, Downtown")
                .build();
        lot = lotRepository.save(lot);

        createFloor(lot, 0, "G",
                List.of(
                        SpotConfig.of("G-C1", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("G-C2", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("G-C3", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("G-B1", ParkingSpotType.BIKE_ONLY, VehicleType.BIKE),
                        SpotConfig.of("G-B2", ParkingSpotType.BIKE_ONLY, VehicleType.BIKE),
                        SpotConfig.of("G-T1", ParkingSpotType.LARGE, VehicleType.TRUCK)
                ));

        createFloor(lot, 1, "1",
                List.of(
                        SpotConfig.of("1-C1", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("1-C2", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("1-C3", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("1-C4", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("1-B1", ParkingSpotType.BIKE_ONLY, VehicleType.BIKE),
                        SpotConfig.of("1-T1", ParkingSpotType.LARGE, VehicleType.TRUCK)
                ));

        createFloor(lot, 2, "2",
                List.of(
                        SpotConfig.of("2-C1", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("2-C2", ParkingSpotType.COMPACT, VehicleType.CAR),
                        SpotConfig.of("2-B1", ParkingSpotType.BIKE_ONLY, VehicleType.BIKE),
                        SpotConfig.of("2-T1", ParkingSpotType.LARGE, VehicleType.TRUCK)
                ));

        log.info("Parking lot initialized: {} floors, {} spots",
                lotRepository.count(), spotRepository.count());
    }

    private void createFloor(ParkingLotEntity lot, int number, String name, List<SpotConfig> configs) {
        ParkingFloorEntity floor = ParkingFloorEntity.builder()
                .parkingLot(lot)
                .floorNumber(number)
                .name(name)
                .build();
        floor = floorRepository.save(floor);

        for (SpotConfig config : configs) {
            ParkingSpotEntity spot = ParkingSpotEntity.builder()
                    .spotIdentifier(config.identifier)
                    .floor(floor)
                    .spotType(config.type)
                    .isOccupied(false)
                    .build();
            spot.setAllowedTypes(config.allowedTypes);
            spotRepository.save(spot);
        }
    }

    private record SpotConfig(String identifier, ParkingSpotType type, Set<VehicleType> allowedTypes) {
        static SpotConfig of(String identifier, ParkingSpotType type, VehicleType... types) {
            return new SpotConfig(identifier, type, Set.of(types));
        }
    }
}
