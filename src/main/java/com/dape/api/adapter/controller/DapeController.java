package com.dape.api.adapter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dape")
public class DapeController {
    @GetMapping("/helloworld")
    public String helloWorld(){
        return "Hello World!";
    }
}
