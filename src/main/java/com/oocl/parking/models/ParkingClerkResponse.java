package com.oocl.parking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parking.domain.ParkingClerk;

import java.util.Objects;

public class ParkingClerkResponse {
    private Long id;

    private Long employeeId;

    private String accountName;

    private String parking_status;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    private static ParkingClerkResponse create(Long id, Long employeeId, String name, String status){
        Objects.requireNonNull(name);
        Objects.requireNonNull(employeeId);
        Objects.requireNonNull(id);
        final ParkingClerkResponse response = new ParkingClerkResponse();
        response.setId(id);
        response.setEmployeeId(employeeId);
        response.setAccountName(name);
        response.setParking_status(status);
        return response;

    }
    public static ParkingClerkResponse create(ParkingClerk entity)
    {
        return create(entity.getId(),entity.getEmployee().getId(),entity.getEmployee().getAccountName(),entity.getParkingStatus());
    }

    @JsonIgnore
    public boolean isValid(){
        return accountName != null;
    }


    public String getParking_status() {
        return parking_status;
    }

    public void setParking_status(String parking_status) {
        this.parking_status = parking_status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
