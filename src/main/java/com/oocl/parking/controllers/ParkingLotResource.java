package com.oocl.parking.controllers;


import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingLotResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

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

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ParkingLot> updateOrder(@RequestBody ParkingLot lot, @PathVariable Long id)
    {
        Optional<ParkingLot> thisLot = parkingLotRepository.findById(id);
        if (!thisLot.isPresent())
            return ResponseEntity.notFound().build();
        lot.setId(id);
        parkingLotRepository.save(lot);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id)
    {
        Optional<ParkingLot> thisLot = parkingLotRepository.findById(id);
        if (!thisLot.isPresent())
            return ResponseEntity.notFound().build();
        parkingLotRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ParkingLotResponse> getById(@PathVariable Long id){
        final Optional<ParkingLot> parkingLot = parkingLotRepository.findById(id);
        if (!parkingLot.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ParkingLotResponse.create(parkingLot.get()));
    }

}
