package com.oocl.parking.domain;


import javax.persistence.*;

@Entity
@Table(name = "parking_clerk")
public class ParkingClerk{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Employee employee;

    @Column(name = "parking_status")
    private String parkingStatus;

    @Column(name = "employee_id")
    private Long employeeId;

    public ParkingClerk(){
        this.parkingStatus = "Available";
    }

    public ParkingClerk(Employee e)
    {
        this.employee = e;
        this.employeeId = e.getId();
        this.parkingStatus = "Available";
    }

    public Long getId() {
        return id;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(String parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public Employee getEmployee() {
        return employee;
    }
}
