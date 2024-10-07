package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.usecase.factory.BetResponseFactory;
import com.dape.api.usecase.service.BetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(BetOperationsController.class);

    public BetOperationsController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping("/bet")
    public ResponseEntity<BetResponse> registerBet(@Valid @RequestBody BetRequest betRequest){
        LOGGER.info("m=registerBet, msg=Método POST chamado para cadastrar uma nova aposta com descrição={} e odd={}", betRequest.getDesBet(), betRequest.getNumOdd());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BetResponseFactory.createBetResponse(betService.registerBet(betRequest)));
    }

    @PatchMapping("/bet/{idtBet}")
    public ResponseEntity<BetResponse> updateBet(@PathVariable Long idtBet, @Valid @RequestBody BetRequest betRequest){
        LOGGER.info("m=updateBet, msg=Método PATCH chamado para atualizar uma aposta com id={} e descrição={} e odd={}", idtBet, betRequest.getDesBet(), betRequest.getNumOdd());
        return ResponseEntity.status(HttpStatus.OK)
                .body(BetResponseFactory.createBetResponse(betService.updateBet(idtBet, betRequest)));
    }

    @PatchMapping("bet/{idtBet}/status")
    public ResponseEntity<BetResponse> updateBetStatus(@PathVariable Long idtBet, @RequestBody BetStatusRequest betStatus){
        LOGGER.info("m=updateBetStatus, msg=Método PATCH chamado para atualizar o status de uma aposta para: {}", betStatus.getBetStatus());
        return ResponseEntity.status(HttpStatus.OK)
                .body(BetResponseFactory.createBetResponse(betService.updateBetStatus(idtBet, betStatus)));
    }
}
