package com.oocl.parking.repositories;

import com.oocl.parking.domain.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder, Long> {
    List<ParkingOrder> findByStatus(String status);
    List<ParkingOrder> findByOwnedByEmployeeId(Long ownedByEmployeeId);
}
