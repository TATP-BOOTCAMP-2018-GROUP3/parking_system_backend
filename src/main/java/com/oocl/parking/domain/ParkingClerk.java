package com.oocl.parking.domain;


import javax.persistence.*;

@Entity
@Table(name = "parking_clerk")
public class ParkingClerk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name", unique = true)
    private String accountName;

    public ParkingClerk(){}

    public ParkingClerk(String name){
        this.accountName = name;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getId() {
        return id;
    }
}
