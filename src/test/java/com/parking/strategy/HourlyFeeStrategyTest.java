package com.parking.strategy;

import com.parking.constants.VehicleType;
import com.parking.entity.TicketEntity;
import com.parking.entity.VehicleEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HourlyFeeStrategyTest {

    private final HourlyFeeStrategy strategy = new HourlyFeeStrategy();

    @Test
    void shouldCalculateFeeForCar() {
        TicketEntity ticket = createTicketWithEntry(LocalDateTime.now().minusHours(3), VehicleType.CAR);
        ticket.setExitTime(LocalDateTime.now());
        double fee = strategy.calculateFee(ticket);
        // 3 hours * $20/hr = $60
        assertEquals(60.0, fee, 0.01);
    }

    @Test
    void shouldEnforceMinimumFee() {
        TicketEntity ticket = createTicketWithEntry(LocalDateTime.now().minusMinutes(15), VehicleType.BIKE);
        ticket.setExitTime(LocalDateTime.now());
        double fee = strategy.calculateFee(ticket);
        // Minimum fee is $10
        assertEquals(10.0, fee, 0.01);
    }

    @Test
    void shouldRoundUpDuration() {
        TicketEntity ticket = createTicketWithEntry(LocalDateTime.now().minusHours(2).minusMinutes(1), VehicleType.CAR);
        ticket.setExitTime(LocalDateTime.now());
        double fee = strategy.calculateFee(ticket);
        // 2h1m -> 3 hours * $20/hr = $60 (ceil)
        assertEquals(60.0, fee, 0.01);
    }

    private TicketEntity createTicketWithEntry(LocalDateTime entryTime, VehicleType type) {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(type);

        TicketEntity ticket = new TicketEntity();
        ticket.setEntryTime(entryTime);
        ticket.setVehicle(vehicle);
        return ticket;
    }
}
