package com.parking.exception;

public class VehicleAlreadyExistsException extends ParkingException {
    public VehicleAlreadyExistsException(String registration) {
        super("Vehicle already exists: " + registration);
    }
}
