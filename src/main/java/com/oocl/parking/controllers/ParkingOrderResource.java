package com.oocl.parking.controllers;

import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingOrderResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
import com.oocl.parking.repositories.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/parkingorders")
public class ParkingOrderResource {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    ParkingLot getParkingLotByParkingOrder(ParkingOrder parkingOrder){
        ParkingLot parkingLot = null;
        if (parkingOrder.getParkingLotId() != null) {
            Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findById(parkingOrder.getParkingLotId());
            if (optionalParkingLot.isPresent()) {
                parkingLot = optionalParkingLot.get();
            }
        }
        return parkingLot;
    }

    @GetMapping
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<ParkingOrderResponse[]> getAll() {
        final ParkingOrderResponse[] orders = parkingOrderRepository.findAll().stream()
                .map(parkingOrder -> ParkingOrderResponse.create(parkingOrder, getParkingLotByParkingOrder(parkingOrder)))
                .toArray(ParkingOrderResponse[]::new);
        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ParkingOrderResponse> get(@PathVariable Long id)
    {
        final Optional<ParkingOrder> order = parkingOrderRepository.findById(id);
        if(!order.isPresent()){
            return ResponseEntity.notFound().build();
        }
        final ParkingOrderResponse response = ParkingOrderResponse.create(order.get(), getParkingLotByParkingOrder(order.get()));
        return ResponseEntity.ok(response);
    }

    @GetMapping(params = "status")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<ParkingOrderResponse[]> getByStatus(@RequestParam String status)
    {
        final ParkingOrderResponse[] orders = parkingOrderRepository.findByStatus(status).stream()
                .map(parkingOrder -> ParkingOrderResponse.create(parkingOrder, getParkingLotByParkingOrder(parkingOrder)))
                .toArray(ParkingOrderResponse[]::new);
        return ResponseEntity.ok(orders);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity add(@RequestBody ParkingOrder order){
        parkingOrderRepository.save(order);
        return ResponseEntity.created(URI.create("/orders/"+order.getId())).header("Access-Control-Expose-Headers", "Location").build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('CLERK')")
    public ResponseEntity<ParkingOrder> updateOrder(@RequestBody ParkingOrder order, @PathVariable Long id)
    {
        Optional<ParkingOrder> thisOrder = parkingOrderRepository.findById(id);
        if (!thisOrder.isPresent())
            return ResponseEntity.notFound().header("Error","Parking Order not found").build();
        if ((order.getStatus().equals("In Progress"))&&!(thisOrder.get().getStatus().equals("Pending")))
            return ResponseEntity.badRequest().header("Error", "Parking order is not available to grab").build();
        if ((order.getStatus().equals("Completed"))&&!(thisOrder.get().getStatus().equals("In Progress")))
            return ResponseEntity.badRequest().header("Error", "Parking order cannot be completed").build();
        order.setId(id);

        if (order.getStatus().equals("Completed")) {
            ParkingLot parkingLot = parkingLotRepository.getOne(order.getParkingLotId());
            parkingLot.setAvailablePositionCount(parkingLot.getAvailablePositionCount() - 1);
            parkingLotRepository.save(parkingLot);
        }
        
        parkingOrderRepository.save(order);


        return ResponseEntity.ok().build();
    }


}
