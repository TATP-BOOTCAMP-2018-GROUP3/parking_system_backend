package com.oocl.parking.models;

import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;

import java.util.Objects;

public class ParkingOrderResponse {
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

    private static ParkingOrderResponse create(Long id, String carId, String parkingLot, String phoneNumber, Long ownedByEmployeeId, String status)
    {
        Objects.requireNonNull(id);
        Objects.requireNonNull(carId);
        Objects.requireNonNull(phoneNumber);
        final ParkingOrderResponse response = new ParkingOrderResponse();
        response.setId(id);
        response.setCarId(carId);
        response.setParkingLot(parkingLot);
        response.setPhoneNumber(phoneNumber);
        response.setOwnedByEmployeeId(ownedByEmployeeId);
        response.setStatus(status);
        return response;
    }
    public static ParkingOrderResponse create(ParkingOrder entity, ParkingLot parkngLot)
    {
        return create(entity.getId(), entity.getCarId(), parkngLot.getParkingLotName(), entity.getPhoneNumber(), entity.getOwnedByEmployeeId(), entity.getStatus());
    }
}
