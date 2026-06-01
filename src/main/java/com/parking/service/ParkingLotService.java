package com.parking.service;

import com.parking.dto.response.FloorSummaryResponse;
import com.parking.dto.response.ParkingLotStatusResponse;
import com.parking.entity.ParkingFloorEntity;
import com.parking.entity.ParkingLotEntity;
import com.parking.entity.ParkingSpotEntity;
import com.parking.repository.ParkingFloorRepository;
import com.parking.repository.ParkingLotRepository;
import com.parking.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingLotService {

    private final ParkingLotRepository lotRepository;
    private final ParkingFloorRepository floorRepository;
    private final ParkingSpotRepository spotRepository;
    private final TicketService ticketService;

    public ParkingLotService(ParkingLotRepository lotRepository, ParkingFloorRepository floorRepository, ParkingSpotRepository spotRepository, TicketService ticketService) {
        this.lotRepository = lotRepository;
        this.floorRepository = floorRepository;
        this.spotRepository = spotRepository;
        this.ticketService = ticketService;
    }

    @Transactional(readOnly = true)
    public ParkingLotStatusResponse getStatus(Long lotId) {
        ParkingLotEntity lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found: " + lotId));

        long totalSpots = spotRepository.countTotalByLotId(lotId);
        long availableSpots = spotRepository.countAvailableByLotId(lotId);
        long occupiedSpots = totalSpots - availableSpots;
        long activeTickets = ticketService.getActiveTicketCount();

        List<ParkingFloorEntity> floors = floorRepository.findByParkingLotIdOrderByFloorNumberAsc(lotId);
        List<FloorSummaryResponse> floorSummaries = floors.stream()
                .map(f -> {
                    List<ParkingSpotEntity> spots = spotRepository.findByFloorIdOrderBySpotIdentifierAsc(f.getId());
                    long total = spots.size();
                    long available = spots.stream().filter(s -> !s.isOccupied()).count();
                    return new FloorSummaryResponse(f.getId(), f.getName(), f.getFloorNumber(),
                            total, available, total - available);
                })
                .toList();

        return new ParkingLotStatusResponse(
                lot.getId(), lot.getName(), lot.getAddress(),
                floors.size(), totalSpots, availableSpots, occupiedSpots,
                activeTickets, availableSpots == 0,
                floorSummaries);
    }
}
