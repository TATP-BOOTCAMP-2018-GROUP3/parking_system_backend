package com.oocl.parking.domain;

import javax.persistence.*;

@Entity
@Table(name = "return_order")
public class ReturnOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private ParkingOrder parkingOrder;

    @Column(name = "parking_order_id")
    private Long parkingOrderId;

    @Column(name = "status")
    private String status;

    @Column(name = "phone_number")
    private String phoneNumber;

    public ReturnOrder(){
        this.status = "Pending";
    }

    public ReturnOrder(Long parkingOrderId, String phoneNumber){
        this.parkingOrderId = parkingOrderId;
        this.phoneNumber = phoneNumber;
        this.status = "Pending";
    }

    public Long getId() {
        return id;
    }

    public Long getParkingOrderId() {
        return parkingOrderId;
    }

    public void setParkingOrderId(Long parkingOrderId) {
        this.parkingOrderId = parkingOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ParkingOrder getParkingOrder() {
        return parkingOrder;
    }

    public void setParkingOrder(ParkingOrder parkingOrder) {
        this.parkingOrder = parkingOrder;
    }
}
