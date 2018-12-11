package com.oocl.parking.factory;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.domain.EmployeeDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EmployeeDetailFactory {
    private EmployeeDetailFactory() {

    }

    public static EmployeeDetail create(Employee employee) {
        return new EmployeeDetail(
                employee.getId(),
                employee.getAccountName(),
                employee.getHashedPassword(),
                employee.getEmail(),
                mapToGrantedAuthorities(employee.getRole())
        );
    }

    private static GrantedAuthority mapToGrantedAuthorities(String role) {
        return new SimpleGrantedAuthority(role);
    }
}
