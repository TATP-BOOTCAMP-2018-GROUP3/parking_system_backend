package com.oocl.parking.utils;

import com.oocl.parking.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeUtilTest {

    private static final String DUMMY_ACCOUNT_NAME = "DUMMY_NAME";
    private static final String DUMMY_EMAIL = "DUMMY_EMAIL";
    private static final String DUMMY_PHONE_NUM = "DUMMY_PHONE_NUM";

    @Test
    public void should_generate_random_string_of_given_length(){
        int length = 8;
        assertEquals(length, EmployeeUtil.generateRandomString(8).length());
    }

    @Test
    public void should_fill_in_employee_default_info() {
        Employee employee = new Employee(DUMMY_ACCOUNT_NAME, DUMMY_EMAIL, DUMMY_PHONE_NUM);
        Employee updatedEmployee = EmployeeUtil.fillEmployeeDefaultInfo(employee);

        assertEquals(DUMMY_ACCOUNT_NAME, updatedEmployee.getAccountName());
        assertEquals(DUMMY_EMAIL, updatedEmployee.getEmail());
        assertEquals(DUMMY_PHONE_NUM, updatedEmployee.getPhoneNum());
        assertEquals(DUMMY_ACCOUNT_NAME, updatedEmployee.getName());
        assertEquals("CLERK", updatedEmployee.getRole());
        assertEquals("On Duty", updatedEmployee.getWorkingStatus());
    }

    @Test
    public void should_fill_in_employee_password_info() {
        Employee employee = new Employee(DUMMY_ACCOUNT_NAME, DUMMY_EMAIL, DUMMY_PHONE_NUM);
        Employee updatedEmployee = EmployeeUtil.fillInEmployeePasswordInfo(employee, "PASSWORD");

        assertNotNull(updatedEmployee.getHashedPassword());
        assertNotNull(updatedEmployee.getToken());
    }

}
