package com.oocl.parking.domain;

import javax.persistence.*;

@Entity
@Table(name = "parking_order")
public class ParkingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_Id")
    private String carId;

    @Column(name = "parking_lot")
    private String parkingLot;

    @Column(name = "status")
    private String status;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "owned_by_employee_id")
    private Long ownedByEmployeeId;

    public ParkingOrder(){
        this.status = "Pending";
    }

    public ParkingOrder(String carId, String parkingLot, String phoneNumber){
        this.carId = carId;
        this.parkingLot = parkingLot;
        this.phoneNumber = phoneNumber;
        this.status = "Pending";
    }

    public ParkingOrder(String carId, String parkingLot, String status, String phoneNumber){
        this.carId = carId;
        this.parkingLot = parkingLot;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId){
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
}
