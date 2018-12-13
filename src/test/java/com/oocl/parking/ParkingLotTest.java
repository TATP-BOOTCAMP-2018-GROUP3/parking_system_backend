package com.oocl.parking;

import com.oocl.parking.domain.ParkingLot;
import com.oocl.parking.models.ParkingLotResponse;
import com.oocl.parking.repositories.ParkingLotRepository;
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

import java.util.Optional;

import static com.oocl.parking.WebTestUtil.getContentAsObject;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotTest {

    private static final String ADMIN_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MiwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTU0NTExNDg4MX0.N-9HALvhp8-Ud8b3stkyetgBhtDLBcwZAxXDWkW-Els";

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
        parkingLotRepository.saveAndFlush(lot);
        //w
        final MvcResult result = mvc.perform(get("/parkinglots")
                                .header("Authorization", "Bearer " + ADMIN_JWT))
                                .andReturn();
        //t
        assertEquals(200, result.getResponse().getStatus());
        final ParkingLotResponse[] responses = getContentAsObject(result, ParkingLotResponse[].class);
        assertEquals("Test1", responses[0].getParkingLotName());
    }

    @Test
    public void post_lot_test() throws Exception{
        //g
        String lotJson = "{\"parkingLotName\":\"Lot2\",\"capacity\":10}";
        //w
        final MvcResult result = mvc.perform(post("/parkinglots")
                .header("Authorization", "Bearer " + ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON).content(lotJson)).andReturn();

        //t
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("Lot2", parkingLotRepository.findAll().get(0).getParkingLotName());
        assertEquals(10, parkingLotRepository.findAll().get(0).getCapacity());

    }

    @Test
    public void put_lot_test() throws Exception{
        //g
        String oldJson = "{\"parkingLotName\":\"Old\",\"capacity\":10}";
        mvc.perform(post("/parkinglots").header("Authorization", "Bearer " + ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON).content(oldJson)).andReturn();
        String lotJson = "{\"parkingLotName\":\"Lot3\",\"capacity\":1}";

        //w
        Long id = parkingLotRepository.findAll().get(0).getId();
        MvcResult result = mvc.perform(put("/parkinglots/"+id).header("Authorization", "Bearer " + ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON).content(lotJson)).andReturn();

        //t
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("Lot3", parkingLotRepository.findAll().get(0).getParkingLotName());
        assertEquals(1, parkingLotRepository.findAll().get(0).getCapacity());

    }

    @Test
    public void delete_lot_test() throws Exception{
        //g
        String oldJson = "{\"parkingLotName\":\"Old\",\"capacity\":10}";
        mvc.perform(post("/parkinglots").header("Authorization", "Bearer " + ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON).content(oldJson)).andReturn();

        //w
        Long id = parkingLotRepository.findAll().get(0).getId();
        MvcResult result = mvc.perform(delete("/parkinglots/"+id).header("Authorization", "Bearer " + ADMIN_JWT)).andReturn();

        //t
        assertEquals(200, result.getResponse().getStatus());
        Optional<ParkingLot> thisLot = parkingLotRepository.findById(id);
        assertFalse(thisLot.isPresent());

    }

    @Test
    public void add_lot_with_invalid_capacity_test() throws Exception
    {
        String oldJson = "{\"parkingLotName\":\"err\",\"capacity\":0}";
        MvcResult result = mvc.perform(post("/parkinglots")
                            .header("Authorization", "Bearer " + ADMIN_JWT)
                            .contentType(MediaType.APPLICATION_JSON).content(oldJson))
                            .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void add_lot_with_duplicate_name_test() throws Exception
    {
        ParkingLot lot = new ParkingLot("err", 10);
        parkingLotRepository.saveAndFlush(lot);
        String lotJson = "{\"parkingLotName\":\"err\",\"capacity\":10}";
        final MvcResult result = mvc.perform(post("/parkinglots")
                .header("Authorization", "Bearer " + ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON).content(lotJson))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());

    }


}
