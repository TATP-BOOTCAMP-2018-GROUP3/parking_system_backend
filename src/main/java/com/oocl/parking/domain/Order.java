package com.oocl.parking.domain;

import javax.persistence.*;

@Entity
@Table(name = "parking_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_Id")
    private String carId;

    @Column(name = "parking_lot")
    private String parkingLot;

    @Column(name = "status")
    private String status;

    public Order(){}
    public Order(String carId){
        this.carId = carId;
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
}
