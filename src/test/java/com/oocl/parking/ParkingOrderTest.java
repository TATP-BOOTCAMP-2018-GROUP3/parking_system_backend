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

    @Test
    public void get_order_by_id_test() throws Exception{
        //g
        ParkingOrder order = new ParkingOrder("Car2", "TempTest2");
        parkingOrderRepository.saveAndFlush(order);
        //w
        final MvcResult result = mvc.perform(get("/orders/"+order.getId())).andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingOrderResponse response = getContentAsObject(result, ParkingOrderResponse.class);
        assertEquals("Car2", response.getCarId());
        assertEquals("TempTest2", response.getParkingLot());
    }

    @Test
    public void post_order_test() throws Exception{
        //g
        String orderJson = "{\"carId\":\"Car3\",\"parkingLot\":\"TempTest3\"}";
        //w
        final MvcResult result = mvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();

        //t
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("Car3", parkingOrderRepository.findAll().get(0).getCarId());

    }
}
