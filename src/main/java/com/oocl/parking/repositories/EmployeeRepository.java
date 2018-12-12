package com.oocl.parking.repositories;

import com.oocl.parking.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByAccountName(String accountName);
    List<Employee> findByaccountName(String accountName);
    List<Employee> findByname(String name);
}
