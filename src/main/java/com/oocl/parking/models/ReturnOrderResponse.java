package com.oocl.parking.models;

import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.domain.ReturnOrder;

import java.util.Objects;

public class ReturnOrderResponse {
    private Long id;
    private String carId;
    private String parkingLot;
    private String phoneNumber;
    private Long ownedByEmployeeId;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getOwnedByEmployeeId() {
        return ownedByEmployeeId;
    }

    public void setOwnedByEmployeeId(Long ownedByEmployeeId) {
        this.ownedByEmployeeId = ownedByEmployeeId;
    }

    private static ReturnOrderResponse create(Long id, String phoneNumber, String status, String carId, String parkingLot, Long ownedByEmployeeId)
    {
        Objects.requireNonNull(id);
        Objects.requireNonNull(phoneNumber);
        Objects.requireNonNull(carId);
        Objects.requireNonNull(parkingLot);
        Objects.requireNonNull(ownedByEmployeeId);
        final ReturnOrderResponse response = new ReturnOrderResponse();
        response.setId(id);
        response.setCarId(carId);
        response.setParkingLot(parkingLot);
        response.setPhoneNumber(phoneNumber);
        response.setOwnedByEmployeeId(ownedByEmployeeId);
        response.setStatus(status);
        return response;
    }
    public static ReturnOrderResponse create(ReturnOrder returnOrder, ParkingOrder parkingOrder)
    {
        return create(returnOrder.getId(), returnOrder.getPhoneNumber(), returnOrder.getStatus(),
                parkingOrder.getCarId(), parkingOrder.getParkingLot(), parkingOrder.getOwnedByEmployeeId());
    }
}
