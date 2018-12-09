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

import static com.oocl.parking.WebTestUtil.getContentAsObject;
import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotTest {
/*
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void clear_repository(){
        parkingLotRepository.deleteAll();
        parkingLotRepository.flush();
    }

    @Test
    public void get_parking_lot_test() throws Exception
    {
        //g
        ParkingLot lot = new ParkingLot("Test1", 10);
        parkingLotkRepository.saveAndFlush(lot);
        //w
        final MvcResult result = mvc.perform(get("/parkinglots")).andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingLotResponse[] responses = getContentAsObject(result, ParkingLotResponse[].class);
        assertEquals("Test1", responses[0].getName());
    }
*/

}
