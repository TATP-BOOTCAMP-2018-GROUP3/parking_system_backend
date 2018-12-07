package com.oocl.parking;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class ParkingClerkTest {
    @Test
    public void test()
    {
        ParkingClerk clerk = new ParkingClerk();
        assertTrue(clerk.test());
    }
}
