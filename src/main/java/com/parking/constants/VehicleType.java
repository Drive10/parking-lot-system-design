package com.parking.constants;

public enum VehicleType {
    CAR(2, 20.0),
    BIKE(1, 10.0),
    TRUCK(4, 50.0);

    private final int size;
    private final double hourlyRate;

    VehicleType(int size, double hourlyRate) {
        this.size = size;
        this.hourlyRate = hourlyRate;
    }

    public int getSize() { return size; }
    public double getHourlyRate() { return hourlyRate; }
}
