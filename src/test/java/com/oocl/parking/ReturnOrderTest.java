package com.oocl.parking;

import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.domain.ParkingOrder;
import com.oocl.parking.domain.ReturnOrder;
import com.oocl.parking.repositories.ParkingLotRepository;
import com.oocl.parking.repositories.ParkingOrderRepository;
import com.oocl.parking.repositories.ReturnOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReturnOrderTest {
    final private static String MAN_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTUFOQUdFUiIsImlkIjozLCJ1c2VybmFtZSI6Im1hbmFnZXIiLCJleHAiOjE1NDUxMjU5MzJ9.G-jb0Kkbgsrtwk4vV0DtW94WePKUZY3MDOXdJ_9Vmeo";

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void clear_repository(){
        parkingOrderRepository.deleteAll();
        parkingOrderRepository.flush();
        returnOrderRepository.deleteAll();
        returnOrderRepository.flush();
        parkingLotRepository.deleteAll();
        parkingLotRepository.flush();
    }

    @Test
    public void get_return_order_test() throws Exception
    {
        //g
        ParkingLot lot = new ParkingLot("TempTest1", 10);
        parkingLotRepository.saveAndFlush(lot);
        ParkingOrder order = new ParkingOrder("Car1", lot.getId(), "12345678");
        parkingOrderRepository.saveAndFlush(order);
        ReturnOrder returnOrder = new ReturnOrder(order.getId(), "123456787");
        returnOrderRepository.saveAndFlush(returnOrder);

        final MvcResult result = mvc.perform(get("/returnorders")
                .header("Authorization", "Bearer " + MAN_JWT))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(lot.getId(), returnOrderRepository.findAll().get(0).getParkingOrderId());

    }
    @Test
    public void get_return_order_by_id_test() throws Exception{
        ParkingLot lot = new ParkingLot("TempTest2", 10);
        parkingLotRepository.saveAndFlush(lot);
        ParkingOrder order = new ParkingOrder("Car2", lot.getId(), "12345678");
        parkingOrderRepository.saveAndFlush(order);
        ReturnOrder returnOrder = new ReturnOrder(order.getId(), "123456787");
        returnOrderRepository.saveAndFlush(returnOrder);
        final  MvcResult result = mvc.perform(get("/returnorders/"+returnOrder.getId())
                .header("Authorization", "Bearer " + MAN_JWT))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(lot.getId(), returnOrderRepository.findAll().get(0).getParkingOrderId());
    }
}
