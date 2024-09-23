package com.dape.api.adapter.controller;

import com.dape.api.adapter.controller.stub.BetRequestStub;
import com.dape.api.adapter.controller.stub.BetPostResponseStub;
import com.dape.api.adapter.controller.stub.BetStub;
import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BetOperationsControllerTest {

    private final BetService betService = Mockito.mock(BetService.class);
    private final BetOperationsController betOperationsController = new BetOperationsController(betService);

    @Test
    void registerBet(){
        final BetRequest betRequest = BetRequestStub.createBetPostRequest();

        final BetResponse betResponse = BetPostResponseStub.createBetPostResponse();

        final Bet bet = BetStub.createBet();

        when(betService.registerBet(betRequest)).thenReturn(bet);

        final ResponseEntity<BetResponse> actualBet = betOperationsController.registerBet(betRequest);
        final ResponseEntity<BetResponse> expectedBet =
                ResponseEntity.status(HttpStatusCode.valueOf(201)).body(betResponse);

        verify(betService).registerBet(betRequest);
        assertEquals(expectedBet.getStatusCode(), actualBet.getStatusCode());
        assertEquals(expectedBet.getBody().getDesBet(), actualBet.getBody().getDesBet());
        assertEquals(expectedBet.getBody().getBetStatusEnum(), actualBet.getBody().getBetStatusEnum());
        assertEquals(expectedBet.getBody().getIdtBet(), actualBet.getBody().getIdtBet());
        assertEquals(expectedBet.getBody().getDatCreated().toLocalDate(), actualBet.getBody().getDatCreated().toLocalDate());
        assertEquals(expectedBet.getBody().getDatUpdated().toLocalDate(), actualBet.getBody().getDatUpdated().toLocalDate());
        assertEquals(expectedBet.getBody().getNumOdd(), actualBet.getBody().getNumOdd());
        assertEquals(expectedBet.getBody().getFlgSelected(), actualBet.getBody().getFlgSelected());
    }

    @Test
    void updateBet(){
        final Long idtBet = 1L;
        final BetRequest betRequest = BetRequestStub.createBetPatchRequest();

        final BetResponse betResponse = BetPostResponseStub.createBetPatchResponse();

        final Bet bet = BetStub.createUpdatedBet();

        when(betService.updateBet(idtBet, betRequest)).thenReturn(bet);

        final ResponseEntity<BetResponse> actualBet = betOperationsController.updateBet(idtBet, betRequest);
        final ResponseEntity<BetResponse> expectedBet =
                ResponseEntity.status(HttpStatusCode.valueOf(200)).body(betResponse);

        verify(betService).updateBet(idtBet, betRequest);
        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }
}
