package com.parking.controller;

import com.parking.constants.PaymentMethod;
import com.parking.constants.VehicleType;
import com.parking.dto.request.ParkVehicleRequest;
import com.parking.dto.response.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ParkingControllerIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void shouldParkAndUnparkVehicle() {
        ParkVehicleRequest request = new ParkVehicleRequest("TEST-1234", "White", VehicleType.CAR);

        ResponseEntity<TicketResponse> parkResponse = rest.postForEntity(
                "/api/v1/parking/park", request, TicketResponse.class);

        assertEquals(HttpStatus.CREATED, parkResponse.getStatusCode());
        TicketResponse ticket = parkResponse.getBody();
        assertNotNull(ticket);
        assertNotNull(ticket.ticketUid());
        assertEquals("TEST-1234", ticket.registrationNumber());
        assertEquals(VehicleType.CAR, ticket.vehicleType());

        // Unpark
        ResponseEntity<TicketResponse> unparkResponse = rest.postForEntity(
                "/api/v1/parking/unpark/" + ticket.ticketUid(), null, TicketResponse.class);

        assertEquals(HttpStatus.OK, unparkResponse.getStatusCode());
        TicketResponse unparked = unparkResponse.getBody();
        assertNotNull(unparked);
        assertTrue(unparked.fee() > 0);
        assertNotNull(unparked.exitTime());
    }

    @Test
    void shouldReturn404ForInvalidTicket() {
        ResponseEntity<String> response = rest.getForEntity(
                "/api/v1/parking/tickets/INVALID", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldProcessPayment() {
        ParkVehicleRequest request = new ParkVehicleRequest("PAYTEST-5678", "Black", VehicleType.CAR);
        ResponseEntity<TicketResponse> parkResponse = rest.postForEntity(
                "/api/v1/parking/park", request, TicketResponse.class);
        String ticketUid = parkResponse.getBody().ticketUid();

        ResponseEntity<com.parking.dto.response.PaymentResponse> paymentResponse = rest.postForEntity(
                "/api/v1/payments/" + ticketUid + "?method=CREDIT_CARD",
                null,
                com.parking.dto.response.PaymentResponse.class);

        assertEquals(HttpStatus.OK, paymentResponse.getStatusCode());
        assertNotNull(paymentResponse.getBody());
        assertTrue(paymentResponse.getBody().amount() > 0);
    }
}
