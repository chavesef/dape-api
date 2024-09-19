package com.dape.api.adapter.controller;

import com.dape.api.adapter.controller.stub.BetPostRequestStub;
import com.dape.api.adapter.controller.stub.BetPostResponseStub;
import com.dape.api.adapter.controller.stub.BetStub;
import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BetOperationsControllerTest {

    private final BetService betService = Mockito.mock(BetService.class);
    private final BetOperationsController betOperationsController = new BetOperationsController(betService);

    @Test
    void registerBet(){
        final BetPostRequest betPostRequest = BetPostRequestStub.createBetPostRequest();

        final BetPostResponse betPostResponse = BetPostResponseStub.createBetPostResponse();

        final Bet bet = BetStub.createBet();

        when(betService.registerBet(betPostRequest)).thenReturn(bet);

        final ResponseEntity<BetPostResponse> actualBet = betOperationsController.registerBet(betPostRequest);
        final ResponseEntity<BetPostResponse> expectedBet =
                ResponseEntity.status(HttpStatusCode.valueOf(201)).body(betPostResponse);

        verify(betService).registerBet(betPostRequest);
        assertEquals(expectedBet.getStatusCode(), actualBet.getStatusCode());
        assertEquals(expectedBet.getBody().getDesBet(), actualBet.getBody().getDesBet());
        assertEquals(expectedBet.getBody().getBetStatusEnum(), actualBet.getBody().getBetStatusEnum());
        assertEquals(expectedBet.getBody().getIdtBet(), actualBet.getBody().getIdtBet());
        assertEquals(expectedBet.getBody().getDatCreated().toLocalDate(), actualBet.getBody().getDatCreated().toLocalDate());
        assertEquals(expectedBet.getBody().getDatUpdated().toLocalDate(), actualBet.getBody().getDatUpdated().toLocalDate());
        assertEquals(expectedBet.getBody().getNumOdd(), actualBet.getBody().getNumOdd());
        assertEquals(expectedBet.getBody().getFlgSelected(), actualBet.getBody().getFlgSelected());
    }
}
