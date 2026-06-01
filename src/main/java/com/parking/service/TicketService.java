package com.parking.service;

import com.parking.constants.TicketStatus;
import com.parking.entity.ParkingSpotEntity;
import com.parking.entity.TicketEntity;
import com.parking.entity.VehicleEntity;
import com.parking.exception.ParkingFullException;
import com.parking.exception.TicketNotFoundException;
import com.parking.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketEntity createTicket(VehicleEntity vehicle, ParkingSpotEntity spot) {
        TicketEntity ticket = TicketEntity.builder()
                .ticketUid(UUID.randomUUID().toString().substring(0, 8).toUpperCase()
                        + "-" + System.currentTimeMillis() % 10000)
                .vehicle(vehicle)
                .spot(spot)
                .entryTime(LocalDateTime.now())
                .fee(0.0)
                .isPaid(false)
                .status(TicketStatus.ACTIVE)
                .build();
        return ticketRepository.save(ticket);
    }

    @Transactional
    public TicketEntity completeTicket(String ticketUid) {
        TicketEntity ticket = ticketRepository.findByTicketUid(ticketUid)
                .orElseThrow(() -> new TicketNotFoundException(ticketUid));

        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Ticket already completed");
        }

        ticket.setExitTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.COMPLETED);
        return ticketRepository.save(ticket);
    }

    public TicketEntity getTicket(String ticketUid) {
        return ticketRepository.findByTicketUid(ticketUid)
                .orElseThrow(() -> new TicketNotFoundException(ticketUid));
    }

    public long getActiveTicketCount() {
        return ticketRepository.countByStatus(TicketStatus.ACTIVE);
    }

    public List<TicketEntity> getActiveTickets() {
        return ticketRepository.findByStatus(TicketStatus.ACTIVE);
    }
}
