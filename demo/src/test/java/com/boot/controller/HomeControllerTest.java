package com.boot.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbaron on 2016-06-11.
 */
public class HomeControllerTest {

    @Test
    public void testHome() {
        HomeController hc = new HomeController();
        String result = hc.home();
        assertEquals(result, "Das Boot home route");
    }
}