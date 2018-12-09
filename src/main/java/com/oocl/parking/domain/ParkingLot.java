package com.oocl.parking.domain;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_lot_name")
    private String parkingLotName;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "available_position_count")
    private int availablePositionCount;

    @Column(name = "employee_id")
    private String employee_id;

    public ParkingLot(){}

    public ParkingLot(String name, int capacity)
    {
        this.parkingLotName = name;
        this.capacity = capacity;
        this.availablePositionCount = capacity;
    }


    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public int getAvailablePositionCount() {
        return availablePositionCount;
    }

    public void setAvailablePositionCount(int availablePositionCount) {
        this.availablePositionCount = availablePositionCount;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }
}

