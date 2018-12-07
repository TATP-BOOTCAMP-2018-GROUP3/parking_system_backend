package com.oocl.parking.domain;


import javax.persistence.*;

@Entity
@Table(name = "parking_clerk")
public class ParkingClerk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_name", unique = true)
    private String accountname;

    protected ParkingClerk(){}

    public ParkingClerk(String name){
        this.accountname = name;
    }


    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public Long getId() {
        return id;
    }
}
