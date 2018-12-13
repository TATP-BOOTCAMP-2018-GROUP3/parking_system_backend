package com.oocl.parking.repositories;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.domain.ParkingClerk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingClerkRepository extends JpaRepository<ParkingClerk, Long> {
    ParkingClerk findByEmployee(Employee employee);
}
