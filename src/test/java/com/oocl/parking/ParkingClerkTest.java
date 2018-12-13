package com.oocl.parking;

import com.oocl.parking.domain.Employee;
import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.repositories.EmployeeRepository;
import com.oocl.parking.repositories.ParkingClerkRepository;
import com.oocl.parking.utils.EmployeeUtil;
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
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingClerkTest {
    private static final String ADMIN_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MiwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTU0NTExNDg4MX0.N-9HALvhp8-Ud8b3stkyetgBhtDLBcwZAxXDWkW-Els";
    private static final String RANDOM_PASSWORD = EmployeeUtil.generateRandomString(6);
    private static final String DUMMY_EMAIL = "dummy@email.com";
    private static final String DUMMY_PHONE_NUM = "12345678";

    @Autowired
    private ParkingClerkRepository parkingClerkRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void clear_repository(){
        parkingClerkRepository.deleteAll();
        employeeRepository.deleteAll();
        employeeRepository.flush();
        parkingClerkRepository.flush();
    }

    @Test
    public void get_parking_clerk_test() throws Exception
    {
        //g
        Employee e = new Employee("Test1", DUMMY_EMAIL, DUMMY_PHONE_NUM);
        e = EmployeeUtil.fillInEmployeePasswordInfo(e, RANDOM_PASSWORD);
        ParkingClerk clerk = new ParkingClerk(e);
        parkingClerkRepository.saveAndFlush(clerk);
        //w
        final MvcResult result = mvc.perform(get("/parkingclerks")
                                .header("Authorization", "Bearer " + ADMIN_JWT))
                                .andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingClerkResponse[] responses = getContentAsObject(result, ParkingClerkResponse[].class);
        assertEquals("Test1", responses[0].getAccountName());
    }

    @Test
    public void post_parking_clerk_test() throws Exception
    {
        //g
        String clerkJson = "{" +
                            "\"accountName\":\"Test2\"," +
                            "\"email\":\"" + DUMMY_EMAIL + "\"," +
                            "\"phoneNum\":\"" + DUMMY_PHONE_NUM + "\"" +
                            " }";
        //w
        final MvcResult result = mvc.perform(post("/parkingclerks")
                                .header("Authorization", "Bearer " + ADMIN_JWT)
                                .contentType(MediaType.APPLICATION_JSON).content(clerkJson))
                                .andReturn();

        //t
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("Test2", parkingClerkRepository.findAll().get(0).getEmployee().getAccountName());
    }

    @Test
    public void test_remove_parking_clerk() throws Exception {
        Employee employee = new Employee("Test3", DUMMY_EMAIL, DUMMY_PHONE_NUM);
        employee = EmployeeUtil.fillInEmployeePasswordInfo(employee, RANDOM_PASSWORD);
        ParkingClerk clerk = new ParkingClerk(employee);
        parkingClerkRepository.saveAndFlush(clerk);

        final MvcResult result = mvc.perform(delete("/parkingclerks/" + clerk.getId())
                                .header("Authorization", "Bearer " + ADMIN_JWT))
                                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertTrue(parkingClerkRepository.findAll().isEmpty());
        assertTrue(employeeRepository.findAll().isEmpty());
    }
}
