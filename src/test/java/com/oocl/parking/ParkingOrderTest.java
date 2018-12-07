package com.oocl.parking;

import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.models.ParkingOrderResponse;
import com.oocl.parking.repositories.ParkingOrderRepository;
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
public class ParkingOrderTest {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void clear_repository(){
        parkingOrderRepository.deleteAll();
        parkingOrderRepository.flush();
    }

    @Test
    public void get_order_test() throws Exception {
        //g
        ParkingOrder order = new ParkingOrder("Car1", "TempTest1");
        parkingOrderRepository.saveAndFlush(order);
        //w
        final MvcResult result = mvc.perform(get("/orders")).andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingOrderResponse[] responses = getContentAsObject(result, ParkingOrderResponse[].class);
        assertEquals("Car1", responses[0].getCarId());
        assertEquals("TempTest1", responses[0].getParkingLot());
    }
}
