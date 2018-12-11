package com.oocl.parking.services;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.models.LoginRequest;
import com.oocl.parking.repositories.EmployeeRepository;
import com.oocl.parking.utils.AuthUtil;
import com.oocl.parking.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    EmployeeRepository employeeRepository;

    public String login(LoginRequest request){
        Employee employee = employeeRepository.findEmployeeByAccountName(request.getAccountName());
        if (employee == null) {
            return null;
        }
        String token = employee.getToken();
        String hashedPassword = employee.getHashedPassword();
        String rehashedPassword = EmployeeUtil.getHashedPassword(request.getPassword(), token);
        if (hashedPassword.equals(rehashedPassword)){
            return AuthUtil.generateJsonWebTokenForEmployee(employee);
        }
        return null;
    }
}
