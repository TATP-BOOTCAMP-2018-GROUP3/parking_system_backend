package com.oocl.parking.controllers;


import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingLotResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/parkinglots")
public class ParkingLotResource {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll(){
        final ParkingLotResponse[] lots = parkingLotRepository.findAll().stream()
                .map(ParkingLotResponse::create)
                .toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(lots);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity add(@RequestBody ParkingLot lot){
        parkingLotRepository.save(lot);
        return ResponseEntity.created(URI.create("/parkinglots/"+lot.getId())).build();
    }
}
