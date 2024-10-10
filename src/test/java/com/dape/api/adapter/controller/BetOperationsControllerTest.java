package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.stub.BetRequestStub;
import com.dape.api.stub.BetResponseStub;
import com.dape.api.stub.BetStatusRequestStub;
import com.dape.api.stub.BetStub;
import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BetOperationsControllerTest {

    public static final Long IDT_BET = 1L;
    private final BetService betService = Mockito.mock(BetService.class);
    private final BetOperationsController betOperationsController = new BetOperationsController(betService);

    @Test
    void registerBet(){
        when(betService.registerBet(any(BetRequest.class))).thenReturn(BetStub.builder().build());

        final BetRequest betRequest = BetRequestStub.builder().build();
        final ResponseEntity<BetResponse> actualBet = betOperationsController.registerBet(betRequest);
        final ResponseEntity<BetResponse> expectedBet =
                ResponseEntity.status(HttpStatusCode.valueOf(201)).body(BetResponseStub.builder().build());

        verify(betService).registerBet(betRequest);
        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBet(){
        when(betService.updateBet(anyLong(), any(BetRequest.class)))
                .thenReturn(BetStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build());

        final BetRequest betRequest = BetRequestStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build();
        final ResponseEntity<BetResponse> actualBet = betOperationsController.updateBet(IDT_BET, betRequest);
        final ResponseEntity<BetResponse> expectedBet = ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(BetResponseStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build());

        verify(betService).updateBet(IDT_BET, betRequest);
        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBetStatus(){
        when(betService.updateBetStatus(anyLong(), any(BetStatusRequest.class))).thenReturn(BetStub.builder().withBetStatusEnum(BetStatusEnum.GREEN).build());

        final BetStatusRequest betStatusRequest = BetStatusRequestStub.builder().withBetStatus(BetStatusEnum.GREEN).build();
        final ResponseEntity<BetResponse> actualBet = betOperationsController.updateBetStatus(IDT_BET, betStatusRequest);
        final ResponseEntity<BetResponse> expectedBet = ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(BetResponseStub.builder().withBetStatusEnum(BetStatusEnum.GREEN).build());

        verify(betService).updateBetStatus(IDT_BET, betStatusRequest);
        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }
}
