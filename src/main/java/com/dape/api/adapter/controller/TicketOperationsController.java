package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.GetTicketsRequest;
import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.dto.response.TicketListResponse;
import com.dape.api.adapter.dto.response.TicketResponse;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.usecase.factory.TicketListReponseFactory;
import com.dape.api.usecase.factory.TicketResponseFactory;
import com.dape.api.usecase.service.TicketService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ResponseEntity<TicketListResponse> getTickets(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                                         @RequestParam(required = false, name = "ticket_status") String ticketStatus,
                                                         @RequestParam(required = false, name = "ticket_type") String ticketType,
                                                         @RequestParam(required = false, name = "idt_client") Long idtClient,
                                                         @RequestParam(required = false, name = "dat_created") String datCreated,
                                                         @RequestParam(required = false, name = "dat_updated") String datUpdated){
        LOGGER.info("m=getTickets, msg=Método GET chamado para buscar bilhetes de apostas cadastrados");
        final Page<Ticket> ticketPage = ticketService.getTicketList
                (new GetTicketsRequest(page, size, ticketStatus, ticketType, idtClient, datCreated, datUpdated));

        return ResponseEntity.status(HttpStatus.OK)
                .body(TicketListReponseFactory.createTicketListResponse(ticketPage));
    }
}
