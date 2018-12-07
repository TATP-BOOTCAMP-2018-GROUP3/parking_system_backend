package com.oocl.parking;

import com.oocl.parking.domain.ParkingClerk;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ParkingClerkTest {
    @Test
    public void test()
    {
        ParkingClerk clerk = new ParkingClerk("Test1");
        assertEquals("Test1", clerk.getAccountname());
    }
}
