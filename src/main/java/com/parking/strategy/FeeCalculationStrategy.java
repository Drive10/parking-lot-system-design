package com.parking.strategy;

import com.parking.entity.TicketEntity;

public interface FeeCalculationStrategy {
    double calculateFee(TicketEntity ticket);
}
