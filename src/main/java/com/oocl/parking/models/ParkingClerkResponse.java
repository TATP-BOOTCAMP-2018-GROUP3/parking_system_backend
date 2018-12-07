package com.oocl.parking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.parking.domain.ParkingClerk;

import java.util.Objects;

public class ParkingClerkResponse {
    private String accountName;


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    private static ParkingClerkResponse create(String name){
        Objects.requireNonNull(name);
        final ParkingClerkResponse response = new ParkingClerkResponse();
        response.setAccountName(name);
        return response;

    }
    public static ParkingClerkResponse create(ParkingClerk entity)
    {
        return create(entity.getAccountName());
    }

    @JsonIgnore
    public boolean isValid(){
        return accountName != null;
    }



}
