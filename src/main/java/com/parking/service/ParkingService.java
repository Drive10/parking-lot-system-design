package com.parking.service;

import com.parking.constants.PaymentMethod;
import com.parking.constants.TicketStatus;
import com.parking.constants.VehicleType;
import com.parking.dto.request.ParkVehicleRequest;
import com.parking.dto.response.TicketResponse;
import com.parking.entity.ParkingSpotEntity;
import com.parking.entity.PaymentEntity;
import com.parking.entity.TicketEntity;
import com.parking.entity.VehicleEntity;
import com.parking.exception.ParkingException;
import com.parking.repository.ParkingSpotRepository;
import com.parking.strategy.FeeCalculationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ParkingService {

    private static final Logger log = LoggerFactory.getLogger(ParkingService.class);

    private final VehicleService vehicleService;
    private final SpotAssignmentService spotAssignmentService;
    private final TicketService ticketService;
    private final PaymentService paymentService;
    private final FeeCalculationStrategy feeStrategy;
    private final ParkingSpotRepository spotRepository;

    public ParkingService(VehicleService vehicleService, SpotAssignmentService spotAssignmentService, TicketService ticketService, PaymentService paymentService, FeeCalculationStrategy feeStrategy, ParkingSpotRepository spotRepository) {
        this.vehicleService = vehicleService;
        this.spotAssignmentService = spotAssignmentService;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.feeStrategy = feeStrategy;
        this.spotRepository = spotRepository;
    }

    private static final Long DEFAULT_LOT_ID = 1L;

    @Transactional
    public TicketResponse parkVehicle(ParkVehicleRequest request) {
        log.info("Parking vehicle: {}", request.registrationNumber());

        VehicleEntity vehicle = vehicleService.getOrCreateVehicle(
                request.registrationNumber(), request.color(), request.vehicleType());

        ParkingSpotEntity spot = spotAssignmentService.assignSpot(DEFAULT_LOT_ID, request.vehicleType());
        log.info("Assigned spot: {} for vehicle: {}", spot.getSpotIdentifier(), request.registrationNumber());

        TicketEntity ticket = ticketService.createTicket(vehicle, spot);

        return toTicketResponse(ticket);
    }

    @Transactional
    public TicketResponse unparkVehicle(String ticketUid) {
        log.info("Unparking vehicle with ticket: {}", ticketUid);

        TicketEntity ticket = ticketService.getTicket(ticketUid);

        if (ticket.getStatus() == TicketStatus.COMPLETED) {
            throw new ParkingException("Ticket already completed: " + ticketUid);
        }

        ticket.setExitTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.COMPLETED);

        double fee = feeStrategy.calculateFee(ticket);
        ticket.setFee(fee);

        ParkingSpotEntity spot = ticket.getSpot();
        spot.setOccupied(false);
        spotRepository.save(spot);

        ticketService.getTicket(ticketUid); // refresh

        log.info("Vehicle exited. Ticket: {}, Fee: ${}", ticketUid, fee);
        return toTicketResponse(ticket);
    }

    public TicketResponse getTicketInfo(String ticketUid) {
        TicketEntity ticket = ticketService.getTicket(ticketUid);
        return toTicketResponse(ticket);
    }

    private TicketResponse toTicketResponse(TicketEntity ticket) {
        return new TicketResponse(
                ticket.getTicketUid(),
                ticket.getVehicle().getRegistrationNumber(),
                ticket.getVehicle().getColor(),
                ticket.getVehicle().getVehicleType(),
                ticket.getSpot().getSpotIdentifier(),
                ticket.getSpot().getFloor().getFloorNumber(),
                ticket.getEntryTime(),
                ticket.getExitTime(),
                ticket.getFee(),
                ticket.getIsPaid(),
                ticket.getStatus()
        );
    }
}
