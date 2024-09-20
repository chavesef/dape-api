package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.usecase.factory.BetPostResponseFactory;
import com.dape.api.usecase.service.BetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<BetPostResponse> registerBet(@Valid @RequestBody BetPostRequest betPostRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BetPostResponseFactory.createBetPostResponse(betService.registerBet(betPostRequest)));
    }

    @PatchMapping("/bet/{idtBet}")
    public ResponseEntity<BetPostResponse> updateBet(@PathVariable Long idtBet, @Valid @RequestBody BetPostRequest betPostRequest){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BetPostResponseFactory.createBetPostResponse(betService.updateBet(idtBet, betPostRequest)));
    }
}
