package com.oocl.parking.models;



import com.oocl.parking.domain.ParkingLot;

import java.util.Objects;

public class ParkingLotResponse {

    private String parkingLotName;

    private int capacity;

    private int availablePositionCount;

    private String employee_id;

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

    private static ParkingLotResponse create(String name, int capacity, int posCount, String employee_id){
        Objects.requireNonNull(name);
        Objects.requireNonNull(capacity);
        final ParkingLotResponse response = new ParkingLotResponse();
        response.setCapacity(capacity);
        response.setAvailablePositionCount(posCount);
        response.setParkingLotName(name);
        response.setEmployee_id(employee_id);
        return response;

    }
    public static ParkingLotResponse create(ParkingLot entity)
    {
        return create(entity.getParkingLotName(), entity.getCapacity(), entity.getAvailablePositionCount(), entity.getEmployee_id());
    }
}
