package com.oocl.parking.models;

import com.oocl.parking.domain.Employee;

import java.util.Objects;

public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNum;
    private String role;
    private String accountName;
    private String workingStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    public static EmployeeResponse create(Employee entity)
    {
        return create(entity.getId(),entity.getName(),entity.getEmail(),entity.getPhoneNum(),entity.getRole(),entity.getAccountName(),entity.getWorkingStatus());
    }

    private static EmployeeResponse create(Long id, String name, String email, String phoneNum, String role, String accountName, String workingStatus) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(id);
        Objects.requireNonNull(accountName);
        final EmployeeResponse response = new EmployeeResponse();
        response.setId(id);
        response.setName(name);
        response.setEmail(email);
        response.setPhoneNum(phoneNum);
        response.setRole(role);
        response.setAccountName(accountName);
        response.setWorkingStatus(workingStatus);
        return response;
    }


}
