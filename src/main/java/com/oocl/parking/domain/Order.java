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

    @Column(name = "parking_lot_id")
    private int parkingLotId;

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

    public int getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
