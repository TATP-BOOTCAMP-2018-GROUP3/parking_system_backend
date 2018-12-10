package com.oocl.parking;

import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.repositories.ParkingClerkRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingClerkTest {
    @Autowired
    private ParkingClerkRepository parkingClerkRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void clear_repository(){
        parkingClerkRepository.deleteAll();
        parkingClerkRepository.flush();
    }

    @Test
    public void get_parking_clerk_test() throws Exception
    {
        //g
        ParkingClerk clerk = new ParkingClerk("Test1");
        parkingClerkRepository.saveAndFlush(clerk);
        //w
        final MvcResult result = mvc.perform(get("/parkingclerks")).andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingClerkResponse[] responses = getContentAsObject(result, ParkingClerkResponse[].class);
        assertEquals("Test1", responses[0].getAccountName());
    }

    @Test
    public void post_parking_clerk_test() throws Exception
    {
        //g
        String clerkJson = "{\"accountName\":\"Test2\"}";
        //w
        final MvcResult result = mvc.perform(post("/parkingclerks")
        .contentType(MediaType.APPLICATION_JSON).content(clerkJson)).andReturn();

        //t
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("Test2", parkingClerkRepository.findAll().get(0).getName());

    }
}
