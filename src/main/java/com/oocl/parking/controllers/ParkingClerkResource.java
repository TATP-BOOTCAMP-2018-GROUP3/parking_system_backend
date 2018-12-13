package com.oocl.parking.controllers;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.models.ParkingOrderResponse;
import com.oocl.parking.models.ReturnOrderResponse;
import com.oocl.parking.repositories.*;
import com.oocl.parking.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/parkingclerks")
public class ParkingClerkResource {
    @Autowired
    private ParkingClerkRepository parkingClerkRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ParkingClerkResponse[]> getAll(){
        final ParkingClerkResponse[] clerks = parkingClerkRepository.findAll().stream()
                .map(ParkingClerkResponse::create)
                .toArray(ParkingClerkResponse[]::new);
        return ResponseEntity.ok(clerks);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ParkingClerkResponse> getById(@PathVariable Long id){
        final Optional<ParkingClerk> parkingClerk = parkingClerkRepository.findById(id);
        if (!parkingClerk.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ParkingClerkResponse.create(parkingClerk.get()));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity add(@RequestBody Employee employee){
        if (employeeRepository.findByAccountName(employee.getAccountName()).size() > 0)
            return ResponseEntity.badRequest().header("Error", "Account Name already exist").build();
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity remove(@PathVariable Long id){
        parkingClerkRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/{id}/parkingorders")
    @PreAuthorize("hasRole('CLERK') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ParkingOrderResponse[]> getOwnedParkingOrders(@PathVariable Long id) {
        final Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent()){
            return ResponseEntity.notFound().build();
        }
        final ParkingClerk parkingClerk = parkingClerkRepository.findByEmployee(e.get());
        if (parkingClerk == null) {
            return ResponseEntity.badRequest().build();
        }
        final ParkingOrderResponse[] orders = parkingOrderRepository.findByOwnedByEmployeeId(parkingClerk.getId()).stream()
                .map(parkingOrder -> ParkingOrderResponse.create(parkingOrder, getParkingLotByParkingOrder(parkingOrder)))
                .toArray(ParkingOrderResponse[]::new);
        return ResponseEntity.ok(orders);
    }

    @PostMapping(path="/{id}/parkingorders", consumes = "application/json")
    @PreAuthorize("hasRole('CLERK') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity grabParkingOrder(@PathVariable Long id, @RequestBody ParkingOrder order) {
        final Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent()){
            return ResponseEntity.notFound().build();
        }
        final ParkingClerk parkingClerk = parkingClerkRepository.findByEmployee(e.get());
        if (parkingClerk == null) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<ParkingOrder> originOrder = parkingOrderRepository.findById(order.getId());
        if (!originOrder.isPresent())
            return ResponseEntity.notFound().header("Error","Parking Order not found").build();
        if ((order.getStatus().equals("In Progress"))&&!(originOrder.get().getStatus().equals("Pending")))
            return ResponseEntity.badRequest().header("Error", "Parking order is not available to grab").build();
        order.setOwnedByEmployeeId(parkingClerk.getId());
        parkingOrderRepository.saveAndFlush(order);
        return ResponseEntity.created(URI.create("/orders/"+order.getId())).header("Access-Control-Expose-Headers", "Location").build();
    }

    @GetMapping(path="/{id}/returnorders")
    @PreAuthorize("hasRole('CLERK') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ReturnOrderResponse[]> getOwnedReturnOrders(@PathVariable Long id) {
        final Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent()){
            return ResponseEntity.notFound().build();
        }
        final ParkingClerk parkingClerk = parkingClerkRepository.findByEmployee(e.get());
        if (parkingClerk == null) {
            return ResponseEntity.badRequest().build();
        }
        final ReturnOrderResponse[] orders = returnOrderRepository.findByOwnedEmployeeId(parkingClerk.getId()).stream()
                .map(returnOrder -> {
                    ParkingOrder parkingOrder = parkingOrderRepository.getOne(returnOrder.getParkingOrderId());
                    return ReturnOrderResponse.create(returnOrder, parkingOrder, getParkingLotByParkingOrder(parkingOrder));
                })
                .toArray(ReturnOrderResponse[]::new);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping(path="/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity updatePatch(@RequestBody ParkingClerk clerk, @PathVariable Long id) {
        final Optional<ParkingClerk> parkingClerk = parkingClerkRepository.findById(id);
        if (!parkingClerk.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ParkingClerk thisClerk = parkingClerk.get();
        if (clerk.getParkingStatus() != null) {
            thisClerk.setParkingStatus(clerk.getParkingStatus());
        }
        parkingClerkRepository.saveAndFlush(thisClerk);
        return ResponseEntity.ok().build();
    }
}
