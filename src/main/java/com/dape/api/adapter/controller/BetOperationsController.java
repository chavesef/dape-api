package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.usecase.service.BetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dape")
public class BetOperationsController {

    private final BetService betService;

    public BetOperationsController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping("/bet")
    public ResponseEntity<BetPostResponse> registerBet(@RequestBody BetPostRequest betPostRequest){
        return new ResponseEntity<>(betService.cadastrarAposta(betPostRequest), HttpStatus.CREATED);
    }
}
