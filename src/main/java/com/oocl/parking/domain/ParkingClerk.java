package com.oocl.parking.domain;


import javax.persistence.*;

@Entity
@Table(name = "parking_clerk")
public class ParkingClerk extends Employee{

    @Id
    private Long id;
    @Column(name = "parking_status")
    private String parking_status = "Pending";

    public ParkingClerk(){}

    public ParkingClerk(String name){
        super(name);
    }


    public Long getId() {
        return id;
    }

    public String getParking_status() {
        return parking_status;
    }

    public void setParking_status(String parking_status) {
        this.parking_status = parking_status;
    }
}
