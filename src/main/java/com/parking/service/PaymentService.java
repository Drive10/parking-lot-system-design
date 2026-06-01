package com.parking.service;

import com.parking.constants.PaymentMethod;
import com.parking.constants.PaymentStatus;
import com.parking.entity.PaymentEntity;
import com.parking.entity.TicketEntity;
import com.parking.exception.TicketNotFoundException;
import com.parking.repository.PaymentRepository;
import com.parking.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;
    private final FeeCalculator feeCalculator;

    public PaymentService(PaymentRepository paymentRepository, TicketRepository ticketRepository, FeeCalculator feeCalculator) {
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
        this.feeCalculator = feeCalculator;
    }

    @Transactional
    public PaymentEntity processPayment(String ticketUid, PaymentMethod method) {
        TicketEntity ticket = ticketRepository.findByTicketUid(ticketUid)
                .orElseThrow(() -> new TicketNotFoundException(ticketUid));

        double fee = feeCalculator.calculateFee(ticket);

        PaymentEntity payment = PaymentEntity.builder()
                .paymentUid(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .ticket(ticket)
                .amount(fee)
                .method(method)
                .status(PaymentStatus.COMPLETED)
                .build();

        ticket.setFee(fee);
        ticket.setIsPaid(true);
        ticketRepository.save(ticket);

        return paymentRepository.save(payment);
    }
}
