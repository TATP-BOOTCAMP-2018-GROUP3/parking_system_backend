package com.oocl.parking.controllers;


import com.oocl.parking.domain.Employee;
import com.oocl.parking.models.EmployeeResponse;
import com.oocl.parking.repositories.EmployeeRepository;
import com.oocl.parking.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<EmployeeResponse[]> getAll()
    {
        final EmployeeResponse[] employees = employeeRepository.findAll().stream()
                .map(EmployeeResponse::create)
                .toArray(EmployeeResponse[]::new);

        return ResponseEntity.ok(employees);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id)
    {
        final Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EmployeeResponse.create(e.get()));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity add(@RequestBody Employee employee){
        if (employeeRepository.findByAccountName(employee.getAccountName()).size() > 0)
            return ResponseEntity.badRequest().header("Error", "Account Name already exist").build();
        String randomPassword = EmployeeUtil.generateRandomString(6);
        employee = EmployeeUtil.fillEmployeeDefaultInfo(employee);
        employee = EmployeeUtil.fillInEmployeePasswordInfo(employee, randomPassword);

        employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/employees/"+employee.getId())
                .header("Content-Type", "application/json")
                .body("{\"initialPassword\": " + "\"" + randomPassword + "\"" + "}");
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity update(@RequestBody Employee employee, @PathVariable Long id){

        Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent())
            return ResponseEntity.notFound().build();
        employee.setId(id);
        employeeRepository.save(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity delete(@PathVariable Long id)
    {
        Optional<Employee> e = employeeRepository.findById(id);
        if (!e.isPresent())
            return ResponseEntity.notFound().build();
        employeeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity updatePatch(@RequestBody Employee employee, @PathVariable Long id)
    {
        Optional<Employee> thisEmployee = employeeRepository.findById(id);
        if (!thisEmployee.isPresent())
            return ResponseEntity.notFound().build();
        Employee insertEmployee = thisEmployee.get();
        if (employee.getAccountName() != null)
        {
            if (!employeeRepository.findByAccountName(employee.getAccountName()).equals(thisEmployee) && !insertEmployee.getAccountName().equals(employee.getAccountName()))
                return ResponseEntity.badRequest().header("Error", "Account Name already exist").build();
            insertEmployee.setAccountName(employee.getAccountName());
        }
        if (employee.getEmail() != null) {insertEmployee.setEmail(employee.getEmail());}
        if (employee.getName() != null){insertEmployee.setName(employee.getName()); }
        if (employee.getPhoneNum() != null ){insertEmployee.setPhoneNum(employee.getPhoneNum());}
        if (employee.getRole()!= null ){insertEmployee.setRole(employee.getRole());}
        if (employee.getWorkingStatus()!= null ){insertEmployee.setWorkingStatus(employee.getWorkingStatus());}
        employeeRepository.save(insertEmployee);
        return ResponseEntity.ok().build();
    }

}
