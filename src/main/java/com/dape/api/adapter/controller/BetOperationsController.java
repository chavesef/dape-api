package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.BetPostRequest;
import com.dape.api.adapter.dto.BetPostResponse;
import com.dape.api.usecase.service.BetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dape")
public class BetOperationsController {

    private final BetService betService;

    public BetOperationsController(BetService betService) {
        this.betService = betService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bet")
    public BetPostResponse registerBet(@RequestBody BetPostRequest betPostRequest){
        return betService.cadastrarAposta(betPostRequest);
    }
}