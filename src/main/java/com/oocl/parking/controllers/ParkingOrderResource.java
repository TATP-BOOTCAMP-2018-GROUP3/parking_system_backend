package com.oocl.parking.controllers;

import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.models.ParkingOrderResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
import com.oocl.parking.repositories.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/parkingorders")
public class ParkingOrderResource {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkinglotRepository;

    @GetMapping
    public ResponseEntity<ParkingOrderResponse[]> getAll() {
        final ParkingOrderResponse[] orders = parkingOrderRepository.findAll().stream()
                .map(parkingOrder -> {
                    Optional<ParkingLot> parkingLot = parkinglotRepository.findById(parkingOrder.getParkingLotId());
                    return ParkingOrderResponse.create(parkingOrder, parkingLot.get());
                })
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
        Optional<ParkingLot> parkingLot = parkinglotRepository.findById(order.get().getParkingLotId());
        final ParkingOrderResponse response = ParkingOrderResponse.create(order.get(), parkingLot.get());
        return ResponseEntity.ok(response);
    }

    @GetMapping(params = "status")
    public ResponseEntity<ParkingOrderResponse[]> getByStatus(@RequestParam String status)
    {
        final ParkingOrderResponse[] orders = parkingOrderRepository.findByStatus(status).stream()
                .map(parkingOrder -> {
                    Optional<ParkingLot> parkingLot = parkinglotRepository.findById(parkingOrder.getParkingLotId());
                    return ParkingOrderResponse.create(parkingOrder, parkingLot.get());
                })
                .toArray(ParkingOrderResponse[]::new);
        return ResponseEntity.ok(orders);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity add(@RequestBody ParkingOrder order){
        parkingOrderRepository.save(order);
        return ResponseEntity.created(URI.create("/orders/"+order.getId())).header("Access-Control-Expose-Headers", "Location").build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ParkingOrder> updateOrder(@RequestBody ParkingOrder order, @PathVariable Long id)
    {
        Optional<ParkingOrder> thisOrder = parkingOrderRepository.findById(id);
        if (!thisOrder.isPresent())
            return ResponseEntity.notFound().build();
        order.setId(id);
        parkingOrderRepository.save(order);
        return ResponseEntity.ok().build();
    }

}
