package com.oocl.parking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.parking.domain.ParkingClerk;

import java.util.Objects;

public class ParkingClerkResponse {
    private String accountName;

    private String parking_status;
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    private static ParkingClerkResponse create(String name, String status){
        Objects.requireNonNull(name);
        final ParkingClerkResponse response = new ParkingClerkResponse();
        response.setAccountName(name);
        response.setParking_status(status);
        return response;

    }
    public static ParkingClerkResponse create(ParkingClerk entity)
    {
        return create(entity.getName(),entity.getParking_status());
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
}
