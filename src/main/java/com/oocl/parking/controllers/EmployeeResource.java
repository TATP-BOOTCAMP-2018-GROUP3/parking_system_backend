package com.oocl.parking.controllers;


import com.oocl.parking.models.EmployeeResponse;
import com.oocl.parking.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeResource {


    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<EmployeeResponse[]> getAll()
    {
        final EmployeeResponse[] employees = employeeRepository.findAll().stream()
                .map(EmployeeResponse::create)
                .toArray(EmployeeResponse[]::new);

        return ResponseEntity.ok(employees);
    }



}
