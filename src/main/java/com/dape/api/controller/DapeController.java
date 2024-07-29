package com.dape.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dape")
public class DapeController {
    @GetMapping("/helloword")
    public String helloWord(){
        return "Hello Word!";
    }
}
