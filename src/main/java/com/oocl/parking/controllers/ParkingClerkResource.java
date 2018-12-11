package com.oocl.parking.controllers;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.repositories.EmployeeRepository;
import com.oocl.parking.repositories.ParkingClerkRepository;
import com.oocl.parking.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/parkingclerks")
public class ParkingClerkResource {
    @Autowired
    private ParkingClerkRepository parkingClerkRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<ParkingClerkResponse[]> getAll(){
        final ParkingClerkResponse[] clerks = parkingClerkRepository.findAll().stream()
                .map(ParkingClerkResponse::create)
                .toArray(ParkingClerkResponse[]::new);
        return ResponseEntity.ok(clerks);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity add(@RequestBody Employee employee){
        String randomPassword = EmployeeUtil.generateRandomString(6);
        employee = EmployeeUtil.fillEmployeeDefaultInfo(employee);
        employee = EmployeeUtil.fillInEmployeePasswordInfo(employee, randomPassword);

        employeeRepository.save(employee);
        ParkingClerk clerk = new ParkingClerk(employee);
        parkingClerkRepository.save(clerk);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/parkingclerks/"+clerk.getId())
                .header("Content-Type", "application/json")
                .body("{\"initialPassword\": " + "\"" + randomPassword + "\"" + "}");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity remove(@PathVariable Long id){
        parkingClerkRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
