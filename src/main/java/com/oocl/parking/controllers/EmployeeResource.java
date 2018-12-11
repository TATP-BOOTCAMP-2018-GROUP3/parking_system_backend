package com.oocl.parking.controllers;


import com.oocl.parking.domain.Employee;
import com.oocl.parking.models.EmployeeResponse;
import com.oocl.parking.repositories.EmployeeRepository;
import com.oocl.parking.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<EmployeeResponse[]> getAll()
    {
        final EmployeeResponse[] employees = employeeRepository.findAll().stream()
                .map(EmployeeResponse::create)
                .toArray(EmployeeResponse[]::new);

        return ResponseEntity.ok(employees);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id)
    {
        final Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EmployeeResponse.create(e.get()));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity add(@RequestBody Employee employee){
        String randomPassword = EmployeeUtil.generateRandomString(6);
        employee = EmployeeUtil.fillEmployeeDefaultInfo(employee);
        employee = EmployeeUtil.fillInEmployeePasswordInfo(employee, randomPassword);

        employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/employees/"+employee.getId())
                .header("Content-Type", "application/json")
                .body("{\"initialPassword\": " + "\"" + randomPassword + "\"" + "}");
    }

}
