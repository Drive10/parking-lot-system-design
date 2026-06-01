package com.parking.exception;

public class TicketNotFoundException extends ParkingException {
    public TicketNotFoundException(String ticketUid) { super("Ticket not found: " + ticketUid); }
}
