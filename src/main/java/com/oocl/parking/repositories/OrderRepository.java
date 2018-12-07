package com.oocl.parking.repositories;

import com.oocl.parking.domain.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<ParkingOrder, Long> {
}
