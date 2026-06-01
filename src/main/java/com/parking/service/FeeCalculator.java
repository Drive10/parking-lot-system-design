package com.parking.service;

import com.parking.entity.TicketEntity;
import com.parking.strategy.FeeCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class FeeCalculator {

    private final FeeCalculationStrategy feeStrategy;

    public FeeCalculator(FeeCalculationStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public double calculateFee(TicketEntity ticket) {
        return feeStrategy.calculateFee(ticket);
    }
}
