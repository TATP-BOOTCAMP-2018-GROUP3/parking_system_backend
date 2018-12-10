package com.oocl.parking.domain;

import com.oocl.parking.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "parking_order")
public class ParkingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_id")
    private String carId;

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "status")
    private String status;

    @Column(name = "phone_num")
    private String phoneNumber;

    @Column(name = "owned_by_employee_id")
    private Long ownedByEmployeeId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id")
    private ParkingLot parkingLot;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id")
    private ParkingClerk clerk;

    public ParkingOrder(){
        this.status = "Pending";
    }

    public ParkingOrder(String carId, String phoneNumber)
    {
        this.carId = carId;
        this.phoneNumber = phoneNumber;
        this.status = "Pending";
    }

    public ParkingOrder(String carId, Long parkingLotId, String phoneNumber){
        this.carId = carId;
        this.parkingLotId = parkingLotId;
        this.phoneNumber = phoneNumber;
        this.status = "Pending";
    }

    public ParkingOrder(String carId, Long parkingLotId, String status, String phoneNumber){
        this.carId = carId;
        this.parkingLotId = parkingLotId;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId){
        this.carId = carId;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
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

    public Long getOwnedByEmployeeId() {
        return ownedByEmployeeId;
    }

    public void setOwnedByEmployeeId(Long ownedByEmployeeId) {
        this.ownedByEmployeeId = ownedByEmployeeId;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingClerk getClerk() {
        return clerk;
    }

    public void setClerk(ParkingClerk clerk) {
        this.clerk = clerk;
    }
}
