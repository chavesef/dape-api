package com.dape.api.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DapeControllerTest {
    private final DapeController dapeController = new DapeController();

    @Test
    void helloWorld(){
        String result = dapeController.helloWorld();
        String expected = "Hello World!";
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
