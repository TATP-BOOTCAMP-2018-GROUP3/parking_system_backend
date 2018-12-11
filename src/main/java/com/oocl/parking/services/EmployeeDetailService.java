package com.oocl.parking.services;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.domain.EmployeeDetail;
import com.oocl.parking.factory.EmployeeDetailFactory;
import com.oocl.parking.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByAccountName(username);

        if(employee == null){
            throw new UsernameNotFoundException(String.format("No user found with accountname '%s'.", username));
        }
        return EmployeeDetailFactory.create(employee);
    }
}
