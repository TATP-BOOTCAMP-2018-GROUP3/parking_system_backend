package com.oocl.parking.controllers;

import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.repositories.ParkingClerkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/parkingclerks")
public class ParkingClerkResource {
    @Autowired
    private ParkingClerkRepository parkingClerkRepository;

    @GetMapping
    public ResponseEntity<ParkingClerkResponse[]> getAll(){
        final ParkingClerkResponse[] clerks = parkingClerkRepository.findAll().stream()
                .map(ParkingClerkResponse::create)
                .toArray(ParkingClerkResponse[]::new);
        return ResponseEntity.ok(clerks);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity add(@RequestBody ParkingClerk clerk){
        parkingClerkRepository.save(clerk);
        return ResponseEntity.created(URI.create("/parkingclerks/"+clerk.getId())).build();
    }
}
