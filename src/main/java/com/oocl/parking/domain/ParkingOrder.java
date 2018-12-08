package com.oocl.parking.domain;

import javax.persistence.*;

@Entity
@Table(name = "parking_order")
public class ParkingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "car_Id")
    private String carId;

    @Column(name = "parking_lot")
    private String parkingLot;

    @Column(name = "status")
    private String status = "Default";

    public ParkingOrder(){}
    public ParkingOrder(String carId, String parkingLot){
        this.carId = carId;
        this.parkingLot = parkingLot;
        this.status = "Default";
    }
    public ParkingOrder(String carId, String parkingLot, String status){
        this.carId = carId;
        this.parkingLot = parkingLot;
        this.status = status;
    }

    public void setCarId(String carId){
        this.carId = carId;
    }
    public String getCarId() {
        return carId;
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

    public Long getId() {
        return id;
    }
}
