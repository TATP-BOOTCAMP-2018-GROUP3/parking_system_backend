package com.oocl.parking.controllers;

import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.models.ParkingOrderResponse;
import com.oocl.parking.repositories.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class ParkingOrderResource {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @GetMapping
    public ResponseEntity<ParkingOrderResponse[]> getAll() {
        final ParkingOrderResponse[] orders = parkingOrderRepository.findAll().stream()
                .map(ParkingOrderResponse::create)
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
        final ParkingOrderResponse response = ParkingOrderResponse.create(order.get());
        return ResponseEntity.ok(response);
    }

}
