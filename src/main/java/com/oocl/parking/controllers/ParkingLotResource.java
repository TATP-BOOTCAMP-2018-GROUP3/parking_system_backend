package com.oocl.parking.controllers;


import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingLotResponse;
import com.oocl.parking.repositories.ParkingClerkRepository;
import com.oocl.parking.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/parkinglots")
public class ParkingLotResource {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingClerkRepository parkingClerkRepository;

    @GetMapping
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<ParkingLotResponse[]> getAll(){
        final ParkingLotResponse[] lots = parkingLotRepository.findAll().stream()
                .map(ParkingLotResponse::create)
                .toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(lots);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<ParkingLotResponse> getById(@PathVariable Long id){
        final Optional<ParkingLot> parkingLot = parkingLotRepository.findById(id);
        if (!parkingLot.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ParkingLotResponse.create(parkingLot.get()));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity add(@RequestBody ParkingLot lot){
        lot.setAvailablePositionCount(lot.getCapacity());
        if (!lot.isCapacityValid())
            return ResponseEntity.badRequest().header("Error", "ParkingLot capacity value is invalid!").build();
        if (!parkingLotRepository.findByparkingLotName(lot.getParkingLotName()).isEmpty())
            return ResponseEntity.badRequest().header("Error", "ParkingLot name already exist!").build();
        parkingLotRepository.save(lot);
        return ResponseEntity.created(URI.create("/parkinglots/"+lot.getId())).build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ParkingLot> update(@RequestBody ParkingLot lot, @PathVariable Long id)
    {
        Optional<ParkingLot> thisLot = parkingLotRepository.findById(id);
        if (!thisLot.isPresent())
            return ResponseEntity.notFound().build();
        if (lot.getEmployeeId()!=null)
        {
            Optional<ParkingClerk> clerk = parkingClerkRepository.findById(lot.getEmployeeId());
            if (!clerk.isPresent())
                return ResponseEntity.badRequest().header("Error", "Parking Clerk not Found").build();
        }
        lot.setId(id);
        parkingLotRepository.save(lot);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity delete(@PathVariable Long id)
    {
        Optional<ParkingLot> thisLot = parkingLotRepository.findById(id);
        if (!thisLot.isPresent())
            return ResponseEntity.notFound().build();
        parkingLotRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<ParkingLotResponse> updateOrder(@RequestBody ParkingLot lot, @PathVariable Long id)
    {
        Optional<ParkingLot> thisLot = parkingLotRepository.findById(id);
        if (!thisLot.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ParkingLot originLot = thisLot.get();

        if (lot.isCapacityValid()) {
            int numberOfCar = originLot.getCapacity() - originLot.getAvailablePositionCount();
            originLot.setCapacity(lot.getCapacity());
            originLot.setAvailablePositionCount(lot.getCapacity() - numberOfCar);
        }
        if (lot.getEmployeeId() != null) originLot.setEmployeeId(lot.getEmployeeId());
        if (lot.getParkingLotName() != null) originLot.setParkingLotName(lot.getParkingLotName());
        if (lot.getStatus() != null) originLot.setStatus(lot.getStatus());
        parkingLotRepository.saveAndFlush(originLot);
        return ResponseEntity.ok().build();
    }

}
