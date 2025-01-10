package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.dto.response.TicketResponse;
import com.dape.api.usecase.factory.TicketResponseFactory;
import com.dape.api.usecase.service.TicketService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dape/ticket")
public class TicketOperationsController {

    private final TicketService ticketService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketOperationsController.class);

    public TicketOperationsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> registerTicket(@Valid @RequestBody TicketRequest ticketRequest){
        LOGGER.info("m=registerTicket, msg=Método POST chamado para cadastrar um bilhete de apostas");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TicketResponseFactory.createTicketResponse(ticketService.registerTicket(ticketRequest)));
    }
}
