package com.oocl.parking.controllers;

import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.domain.ReturnOrder;
import com.oocl.parking.models.ReturnOrderResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
import com.oocl.parking.repositories.ParkingOrderRepository;
import com.oocl.parking.repositories.ReturnOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/returnorders")
public class ReturnOrderResource {

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkinglotRepository;

    ParkingLot getParkingLotByParkingOrder(ParkingOrder parkingOrder){
        ParkingLot parkingLot = null;
        if (parkingOrder.getParkingLotId() != null) {
            Optional<ParkingLot> optionalParkingLot = parkinglotRepository.findById(parkingOrder.getParkingLotId());
            if (optionalParkingLot.isPresent()) {
                parkingLot = optionalParkingLot.get();
            }
        }
        return parkingLot;
    }

    @GetMapping
    public ResponseEntity<ReturnOrderResponse[]> getAll() {
        final ReturnOrderResponse[] orders = returnOrderRepository.findAll().stream()
                .map(returnOrder -> {
                    ParkingOrder parkingOrder = parkingOrderRepository.getOne(returnOrder.getParkingOrderId());
                    return ReturnOrderResponse.create(returnOrder, parkingOrder, getParkingLotByParkingOrder(parkingOrder));
                })
                .toArray(ReturnOrderResponse[]::new);
        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReturnOrderResponse> get(@PathVariable Long id)
    {
        final Optional<ReturnOrder> order = returnOrderRepository.findById(id);
        if(!order.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Optional<ParkingOrder> parkingOrder = parkingOrderRepository.findById(order.get().getParkingOrderId());
        if (!(parkingOrder).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        final ReturnOrderResponse response = ReturnOrderResponse.create(order.get(), parkingOrder.get(), getParkingLotByParkingOrder(parkingOrder.get()));
        return ResponseEntity.ok(response);
    }

    @GetMapping(params = "status")
    public ResponseEntity<ReturnOrderResponse[]> getByStatus(@RequestParam String status)
    {
        final ReturnOrderResponse[] orders = returnOrderRepository.findByStatus(status).stream()
                .map(returnOrder -> {
                    ParkingOrder parkingOrder = parkingOrderRepository.getOne(returnOrder.getParkingOrderId());
                    return ReturnOrderResponse.create(returnOrder, parkingOrder, getParkingLotByParkingOrder(parkingOrder));
                })
                .toArray(ReturnOrderResponse[]::new);
        return ResponseEntity.ok(orders);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity add(@RequestBody ReturnOrder order){
        Optional<ParkingOrder> parkingOrder = parkingOrderRepository.findById(order.getParkingOrderId());
        if (!(parkingOrder).isPresent() || !(parkingOrder.get().getStatus().equals("Completed") || parkingOrder.get().getParkingLotId() == null)){
            return ResponseEntity.badRequest().build();
        }
        returnOrderRepository.saveAndFlush(order);
        return ResponseEntity.created(URI.create("/orders/"+order.getId())).header("Access-Control-Expose-Headers", "Location").build();
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ReturnOrderResponse> updateOrder(@RequestBody ReturnOrder order, @PathVariable Long id)
    {
        Optional<ReturnOrder> thisOrder = returnOrderRepository.findById(id);
        if (!thisOrder.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ReturnOrder originOrder = thisOrder.get();
        if (order.getParkingOrderId() != null) {
            Optional<ParkingOrder> parkingOrder = parkingOrderRepository.findById(order.getParkingOrderId());
            if (parkingOrder.isPresent() && (parkingOrder.get().getStatus().equals("Completed") && parkingOrder.get().getParkingLotId() != null)) {
                originOrder.setParkingOrderId(order.getParkingOrderId());
            }
        }
        if (order.getPhoneNumber() != null) originOrder.setPhoneNumber(order.getPhoneNumber());
        if (order.getStatus() != null) originOrder.setStatus(order.getStatus());

        returnOrderRepository.saveAndFlush(originOrder);
        return ResponseEntity.ok().build();
    }
}
