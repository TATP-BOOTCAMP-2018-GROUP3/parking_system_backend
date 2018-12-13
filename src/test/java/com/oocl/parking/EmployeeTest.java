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
import static junit.framework.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {

    private static final String CLERK_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQ0xFUksiLCJpZCI6MSwidXNlcm5hbWUiOiJjbGVyayIsImV4cCI6MTU0NTAzNzgzOH0.sWgx_dU0jx5_MOtGbC_1sgaX92HNJJFllIL9Ff6Q4Q0";

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

        final MvcResult result = mvc.perform(get("/employees").header("Authorization", "Bearer " + CLERK_JWT)).andReturn();

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
        final MvcResult result = mvc.perform(get("/employees/"+e.getId()).header("Authorization", "Bearer " + CLERK_JWT)).andReturn();
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
        final MvcResult result = mvc.perform(post("/employees").header("Authorization", "Bearer " + CLERK_JWT)
                .contentType(MediaType.APPLICATION_JSON).content(employeeJson)).andReturn();

        //t
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("Test3", employeeRepository.findAll().get(0).getAccountName());
    }

    @Test
    public void patch_employee_test() throws Exception
    {
        String employeeJson = "{" +
                "\"name\":\"myname3\"," +
                "\"accountName\":\"Test3\"," +
                "\"email\":\"email3\"," +
                "\"phoneNum\":\"123\"" +
                " }";
        mvc.perform(post("/employees").header("Authorization", "Bearer " + CLERK_JWT)
             .contentType(MediaType.APPLICATION_JSON).content(employeeJson)).andReturn();
        String patchJson = "{" +
                "\"name\":\"updateName\"" +
                " }";
        final MvcResult result = mvc.perform(patch("/employees/"+employeeRepository.findAll().get(0).getId()).header("Authorization", "Bearer " + CLERK_JWT).contentType(MediaType.APPLICATION_JSON)
            .content(patchJson)).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("updateName", employeeRepository.findAll().get(0).getName());
    }

    @Test
    public void patch_employee_with_exist_account_name_test() throws Exception
    {
        Employee e1 = new Employee("AccountName1", "email", "phonenum");
        Employee e2 = new Employee("AccountName2", "email", "phonenum");
        employeeRepository.saveAndFlush(e1);
        employeeRepository.saveAndFlush(e2);
        String patchJson = "{" +
                "\"accountName\":\"AccountName2\"" +
                " }";
        final MvcResult result = mvc.perform(patch("/employees/"+employeeRepository.findAll().get(0).getId())
            .header("Authorization", "Bearer " + CLERK_JWT).contentType(MediaType.APPLICATION_JSON).content(patchJson)).andReturn();
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("AccountName1", employeeRepository.findAll().get(0).getAccountName());

    }
    @Test
    public void delete_employee_by_id_test() throws Exception
    {
        employeeRepository.saveAndFlush(new Employee("Delete1", "email", "phonenum"));
        Long id = employeeRepository.findAll().get(0).getId();
        final MvcResult result = mvc.perform(delete("/employees/"+id)
                .header("Authorization", "Bearer " + CLERK_JWT)).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertFalse(employeeRepository.existsById(id));

    }

}
