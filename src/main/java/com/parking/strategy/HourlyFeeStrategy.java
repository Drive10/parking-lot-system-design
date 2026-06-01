package com.parking.strategy;

import com.parking.entity.TicketEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HourlyFeeStrategy implements FeeCalculationStrategy {

    private static final double MINIMUM_FEE = 10.0;

    @Override
    public double calculateFee(TicketEntity ticket) {
        LocalDateTime end = ticket.getExitTime() != null ? ticket.getExitTime() : LocalDateTime.now();
        long minutes = Duration.between(ticket.getEntryTime(), end).toMinutes();
        long hours = (long) Math.ceil(minutes / 60.0);
        double rate = ticket.getVehicle().getVehicleType().getHourlyRate();
        return Math.max(hours * rate, MINIMUM_FEE);
    }
}
