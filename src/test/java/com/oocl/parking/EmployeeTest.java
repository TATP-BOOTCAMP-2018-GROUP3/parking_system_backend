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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.oocl.parking.WebTestUtil.getContentAsObject;
import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    @Test
    public void get_employee_by_id_test() throws Exception
    {
        Employee e = new Employee("test2", "email2", "123");
        employeeRepository.saveAndFlush(e);
        final MvcResult result = mvc.perform(get("/employees/"+e.getId())).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        final EmployeeResponse responses = getContentAsObject(result, EmployeeResponse.class);
        assertEquals("test2", responses.getAccountName());
        assertEquals("email2", responses.getEmail());
        assertEquals("123", responses.getPhoneNum());
    }
    @Test
    public void post_employee_test() throws Exception
    {
        //g
        String employeeJson = "{" +
                "\"accountName\":\"Test3\"," +
                "\"email\":\"email3\"," +
                "\"phoneNum\":\"123\"" +
                " }";
        //w
        final MvcResult result = mvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content(employeeJson)).andReturn();

        //t
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("Test3", employeeRepository.findAll().get(0).getAccountName());
    }

}
