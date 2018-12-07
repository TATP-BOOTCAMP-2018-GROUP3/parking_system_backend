package com.oocl.parking.models;

import com.oocl.parking.domain.ParkingOrder;

import java.util.Objects;

public class ParkingOrderResponse {
    private String carId;
    private String parkingLot;
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

    private static ParkingOrderResponse create(String carId, String parkingLot, String status)
    {
        Objects.requireNonNull(carId);
        final ParkingOrderResponse response = new ParkingOrderResponse();
        response.setCarId(carId);
        response.setParkingLot(parkingLot);
        response.setStatus(status);
        return response;
    }
    public static ParkingOrderResponse create(ParkingOrder entity)
    {
        return create(entity.getCarId(),entity.getParkingLot(),entity.getParkingLot());
    }
}
