package com.oocl.parking.models;

import com.oocl.parking.domain.ParkingOrder;

import java.util.Objects;

public class ParkingOrderResponse {
    private String carId;
    private String parkingLot;
    private String phoneNumber;
    private String status;


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private static ParkingOrderResponse create(String carId, String parkingLot, String phoneNumber, String status)
    {
        Objects.requireNonNull(carId);
        Objects.requireNonNull(phoneNumber);
        final ParkingOrderResponse response = new ParkingOrderResponse();
        response.setCarId(carId);
        response.setParkingLot(parkingLot);
        response.setPhoneNumber(phoneNumber);
        response.setStatus(status);
        return response;
    }
    public static ParkingOrderResponse create(ParkingOrder entity)
    {
        return create(entity.getCarId(), entity.getParkingLot(), entity.getPhoneNumber(), entity.getStatus());
    }
}
