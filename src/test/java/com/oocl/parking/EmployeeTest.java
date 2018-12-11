package com.oocl.parking;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.models.EmployeeResponse;
import com.oocl.parking.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.oocl.parking.WebTestUtil.getContentAsObject;
import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void clear_repository(){

        employeeRepository.deleteAll();
        employeeRepository.flush();
    }

    @Test
    public void get_employee_test() throws Exception
    {
        Employee e =new Employee("test1","Email1","123");
        employeeRepository.saveAndFlush(e);

        final MvcResult result = mvc.perform(get("/employees")).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        final EmployeeResponse[] responses = getContentAsObject(result, EmployeeResponse[].class);

        assertEquals("test1", responses[0].getAccountName());
        assertEquals("Email1", responses[0].getEmail());
        assertEquals("123", responses[0].getPhoneNum());
    }

}
