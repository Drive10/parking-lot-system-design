package com.parking.controller;

import com.parking.constants.PaymentMethod;
import com.parking.dto.response.ParkingLotStatusResponse;
import com.parking.dto.response.PaymentResponse;
import com.parking.entity.PaymentEntity;
import com.parking.service.ParkingLotService;
import com.parking.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Operations", description = "Payments and status")
public class OperationsController {

    private final PaymentService paymentService;
    private final ParkingLotService parkingLotService;

    public OperationsController(PaymentService paymentService, ParkingLotService parkingLotService) {
        this.paymentService = paymentService;
        this.parkingLotService = parkingLotService;
    }

    @PostMapping("/payments/{ticketUid}")
    @Operation(summary = "Process payment for a ticket")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable String ticketUid,
            @RequestParam(defaultValue = "CASH") PaymentMethod method) {
        PaymentEntity payment = paymentService.processPayment(ticketUid, method);
        return ResponseEntity.ok(new PaymentResponse(
                payment.getPaymentUid(),
                payment.getTicket().getTicketUid(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getCreatedAt()
        ));
    }

    @GetMapping("/lots/{lotId}/status")
    @Operation(summary = "Get parking lot status")
    public ResponseEntity<ParkingLotStatusResponse> getLotStatus(@PathVariable Long lotId) {
        return ResponseEntity.ok(parkingLotService.getStatus(lotId));
    }
}
