package com.oocl.parking.repositories;

import com.oocl.parking.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
