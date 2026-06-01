package com.parking.controller;

import com.parking.dto.request.ParkVehicleRequest;
import com.parking.dto.response.TicketResponse;
import com.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking")
@Tag(name = "Parking Operations", description = "Park and unpark vehicles")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/park")
    @Operation(summary = "Park a vehicle", description = "Assigns a spot and issues a ticket")
    @ApiResponse(responseCode = "201", description = "Vehicle parked successfully")
    @ApiResponse(responseCode = "409", description = "Parking lot full or conflict")
    public ResponseEntity<TicketResponse> parkVehicle(@Valid @RequestBody ParkVehicleRequest request) {
        TicketResponse response = parkingService.parkVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/unpark/{ticketUid}")
    @Operation(summary = "Unpark a vehicle", description = "Completes the ticket and calculates fee")
    @ApiResponse(responseCode = "200", description = "Vehicle unparked successfully")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    public ResponseEntity<TicketResponse> unparkVehicle(@PathVariable String ticketUid) {
        TicketResponse response = parkingService.unparkVehicle(ticketUid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tickets/{ticketUid}")
    @Operation(summary = "Get ticket info", description = "Retrieve ticket details by UID")
    @ApiResponse(responseCode = "200", description = "Ticket found")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable String ticketUid) {
        TicketResponse response = parkingService.getTicketInfo(ticketUid);
        return ResponseEntity.ok(response);
    }
}
