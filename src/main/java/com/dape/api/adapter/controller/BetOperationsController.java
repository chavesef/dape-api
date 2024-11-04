package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.adapter.dto.request.GetBetsRequest;
import com.dape.api.adapter.dto.response.BetListResponse;
import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.usecase.factory.BetListResponseFactory;
import com.dape.api.usecase.factory.BetResponseFactory;
import com.dape.api.usecase.service.BetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dape/bet")
public class BetOperationsController {

    private final BetService betService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BetOperationsController.class);

    public BetOperationsController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping
    public ResponseEntity<BetResponse> registerBet(@Valid @RequestBody BetRequest betRequest){
        LOGGER.info("m=registerBet, msg=Método POST chamado para cadastrar uma nova aposta com descrição={} e odd={}", betRequest.getDesBet(), betRequest.getNumOdd());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BetResponseFactory.createBetResponse(betService.registerBet(betRequest)));
    }

    @PatchMapping("/{idtBet}")
    public ResponseEntity<BetResponse> updateBet(@PathVariable Long idtBet, @Valid @RequestBody BetRequest betRequest){
        LOGGER.info("m=updateBet, msg=Método PATCH chamado para atualizar uma aposta com id={} e descrição={} e odd={}", idtBet, betRequest.getDesBet(), betRequest.getNumOdd());
        return ResponseEntity.status(HttpStatus.OK)
                .body(BetResponseFactory.createBetResponse(betService.updateBet(idtBet, betRequest)));
    }

    @PatchMapping("/{idtBet}/status")
    public ResponseEntity<BetResponse> updateBetStatus(@PathVariable Long idtBet, @RequestBody BetStatusRequest betStatusRequest){
        LOGGER.info("m=updateBetStatus, msg=Método PATCH chamado para atualizar o status de uma aposta para: {}", betStatusRequest.getBetStatus());
        return ResponseEntity.status(HttpStatus.OK)
                .body(BetResponseFactory.createBetResponse(betService.updateBetStatus(idtBet, betStatusRequest)));
    }

    @GetMapping
    public ResponseEntity<BetListResponse> getBets(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, name = "bet_status") String betStatus,
                                                  @RequestParam(required = false, name = "dat_created") String datCreated,
                                                  @RequestParam(required = false, name = "dat_updated") String datUpdated){
        LOGGER.info("m=getBet, msg=Método GET chamado para listar apostas cadastradas");
        final Page<Bet> betPage = betService.getBetList(new GetBetsRequest(page, size, betStatus, datCreated, datUpdated));

        return ResponseEntity.status(HttpStatus.OK)
                .body(BetListResponseFactory.createBetListResponse(betPage));
    }
}
