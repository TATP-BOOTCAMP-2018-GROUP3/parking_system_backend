package com.oocl.parking.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingClerkRepository extends JpaRepository<ParkingClerk, Long> {
}
