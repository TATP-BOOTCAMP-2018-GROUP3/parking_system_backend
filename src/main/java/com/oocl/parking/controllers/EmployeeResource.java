package com.oocl.parking.controllers;


import com.oocl.parking.domain.Employee;
import com.oocl.parking.models.EmployeeResponse;
import com.oocl.parking.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @GetMapping(value = "{/id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id)
    {
        final Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EmployeeResponse.create(e.get()));
    }



}
