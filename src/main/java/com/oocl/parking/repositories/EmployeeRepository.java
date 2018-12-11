package com.oocl.parking.repositories;

import com.oocl.parking.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByAccountName(String accountName);
}
