package com.oocl.parking;

import com.oocl.parking.domain.ParkingClerk;
import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.models.ParkingClerkResponse;
import com.oocl.parking.models.ParkingOrderResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingOrderTest {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

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
        ParkingLot lot = new ParkingLot("TempTest1", 10);
        parkingLotRepository.saveAndFlush(lot);
        ParkingOrder order = new ParkingOrder("Car1", lot.getId(), "12345678");
        parkingOrderRepository.saveAndFlush(order);
        //w
        final MvcResult result = mvc.perform(get("/parkingorders")).andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingOrderResponse[] responses = getContentAsObject(result, ParkingOrderResponse[].class);
        assertEquals("Car1", responses[0].getCarId());
        assertEquals("TempTest1", responses[0].getParkingLot());
    }

    @Test
    public void get_order_by_id_test() throws Exception{
        //g
        ParkingLot lot = new ParkingLot("TempTest2", 10);
        parkingLotRepository.saveAndFlush(lot);
        ParkingOrder order = new ParkingOrder("Car2", lot.getId(), "12345678");
        parkingOrderRepository.saveAndFlush(order);
        //w
        final MvcResult result = mvc.perform(get("/parkingorders/"+order.getId())).andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingOrderResponse response = getContentAsObject(result, ParkingOrderResponse.class);
        assertEquals("Car2", response.getCarId());
        assertEquals("TempTest2", response.getParkingLot());
    }

    @Test
    public void post_order_test() throws Exception{
        //g
        ParkingLot lot = new ParkingLot("Lot", 10);
        parkingLotRepository.saveAndFlush(lot);
        String orderJson = "{\"carId\":\"Car3\",\"parkingLotId\":"+lot.getId()+", \"phoneNumber\":\"12345678\"}";
        //w
        final MvcResult result = mvc.perform(post("/parkingorders")
                .contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();

        //t
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("Car3", parkingOrderRepository.findAll().get(0).getCarId());

    }

    @Test
    public void put_order_change_test() throws Exception{
        //g
        ParkingLot lot = new ParkingLot("Lot", 10);
        parkingLotRepository.saveAndFlush(lot);
        String orderJson = "{\"carId\":\"Car4\",\"parkingLotId\":"+lot.getId()+", \"phoneNumber\":\"12345678\"}";
        mvc.perform(post("/parkingorders/").contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();

        Long id = parkingOrderRepository.findAll().get(0).getId();
        String putJson = "{\"carId\":\"Car5\",\"parkingLotId\":"+lot.getId()+", \"phoneNumber\":\"852-87654321\"}";
        //w
        MvcResult result = mvc.perform(put("/parkingorders/"+id)
                .contentType(MediaType.APPLICATION_JSON).content(putJson)).andReturn();

        //t
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("Car5", parkingOrderRepository.findAll().get(0).getCarId());



    }

    @Test
    public void should_get_status_grabbed() throws Exception{
        //g
        ParkingLot lot = new ParkingLot("Lot", 10);
        parkingLotRepository.saveAndFlush(lot);
        String orderJson = "{\"carId\":\"Car3\",\"parkingLotId\":\"1\", \"phoneNumber\":\"12345678\"}";
        //w
        mvc.perform(post("/parkingorders")
                .contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();
        Long id = parkingOrderRepository.findAll().get(0).getId();
        final MvcResult result = mvc.perform(put("/parkingorders/"+id)
                .contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();

                //t
        assertEquals("In Progress", parkingOrderRepository.findAll().get(0).getStatus());
        //assertEquals("Car3", parkingOrderRepository.findAll().get(0).getCarId());

    }

    @Test
    public void should_get_badrequest_when_duplicate_grab_order() throws Exception{
        //g
        ParkingLot lot = new ParkingLot("Lot", 10);
        parkingLotRepository.saveAndFlush(lot);
        String orderJson = "{\"carId\":\"Car3\",\"parkingLotId\":\"1\", \"phoneNumber\":\"12345678\"}";
        //w
        mvc.perform(post("/parkingorders")
                .contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();
        Long id = parkingOrderRepository.findAll().get(0).getId();
        mvc.perform(put("/parkingorders/"+id)
                .contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();
        final MvcResult result = mvc.perform(put("/parkingorders/"+id)
                .contentType(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();

        //t
        assertEquals(400, result.getResponse().getStatus());
    }
}
