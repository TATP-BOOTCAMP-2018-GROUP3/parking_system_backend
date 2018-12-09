package com.oocl.parking.controllers;


import com.oocl.parking.models.ParkingLotResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
