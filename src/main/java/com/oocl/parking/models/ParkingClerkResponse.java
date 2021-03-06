package com.oocl.parking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parking.domain.ParkingClerk;

import java.util.Objects;

public class ParkingClerkResponse {
    private Long id;

    private Long employeeId;

    private String name;

    private String accountName;

    private String parking_status;

    private String phoneNum;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    private static ParkingClerkResponse create(Long id, String name, Long employeeId, String acname, String phoneNum, String status){
        Objects.requireNonNull(name);
        Objects.requireNonNull(acname);
        Objects.requireNonNull(employeeId);
        Objects.requireNonNull(id);
        final ParkingClerkResponse response = new ParkingClerkResponse();
        response.setId(id);
        response.setName(name);
        response.setEmployeeId(employeeId);
        response.setAccountName(acname);
        response.setPhoneNum(phoneNum);
        response.setParking_status(status);
        return response;

    }
    public static ParkingClerkResponse create(ParkingClerk entity)
    {
        return create(entity.getId(),entity.getEmployee().getName(),entity.getEmployee().getId(),entity.getEmployee().getAccountName(),entity.getEmployee().getPhoneNum(),entity.getParkingStatus());
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
