package com.dape.api.adapter.controller;

import com.dape.api.adapter.controller.DapeController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DapeControllerTest {
    private final DapeController dapeController = new DapeController();

    @Test
    void helloWorld(){
        final String result = dapeController.helloWorld();
        final String expected = "Hello World!";
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
