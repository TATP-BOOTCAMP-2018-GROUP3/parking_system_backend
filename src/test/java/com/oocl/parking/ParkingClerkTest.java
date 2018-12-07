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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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
    public void get_parking_boy_test() throws Exception
    {
        //g
        ParkingClerk clerk = new ParkingClerk("Test1");
        //w
        final MvcResult result = mvc.perform(get("/parkingclerks")).andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        //final ParkingClerkResponse[] responses = getContentAsObject(result, ParkingClerkResponse[].class);

    }
}
