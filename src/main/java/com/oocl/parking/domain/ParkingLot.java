package com.oocl.parking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @JsonIgnore
    public boolean isCapacityValid()
    {
        if (this.capacity <= 0)
            return false;
        return true;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_lot_name")
    private String parkingLotName;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "available_position_count")
    private int availablePositionCount;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id")
    private ParkingClerk clerk;

    @Column(name = "employee_id")
    private Long employeeId;

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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingClerk getClerk() {
        return clerk;
    }

    public void setClerk(ParkingClerk clerk) {
        this.clerk = clerk;
    }
}

