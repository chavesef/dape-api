package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.adapter.dto.request.GetBetsRequest;
import com.dape.api.adapter.dto.response.BetListResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.stub.BetListResponseStub;
import com.dape.api.stub.BetRequestStub;
import com.dape.api.stub.BetResponseStub;
import com.dape.api.stub.BetStatusRequestStub;
import com.dape.api.stub.BetStub;
import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

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

        final BetStatusRequest betStatusRequest = BetStatusRequestStub.builder().withBetStatus("GREEN").build();
        final ResponseEntity<BetResponse> actualBet = betOperationsController.updateBetStatus(IDT_BET, betStatusRequest);
        final ResponseEntity<BetResponse> expectedBet = ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(BetResponseStub.builder().withBetStatusEnum(BetStatusEnum.GREEN).build());

        verify(betService).updateBetStatus(IDT_BET, betStatusRequest);
        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void getBets() {
        when(betService.getBetList(any(GetBetsRequest.class))).thenReturn(getBetPage());

        final ResponseEntity<BetListResponse> expectedResponse = ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(BetListResponseStub.builder().build());
        final ResponseEntity<BetListResponse> actualResponse = betOperationsController.getBets(
                getBetPage().getNumber(), getBetPage().getSize(), "PENDING", "2024-10-21", null);

        assertThat(actualResponse).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedResponse);
        verify(betService).getBetList(any(GetBetsRequest.class));
    }

    private Page<Bet> getBetPage() {
        final Pageable pageable = PageRequest.of(0, 10);
        return new PageImpl<>(Collections.singletonList(BetStub.builder().build()), pageable, 1);
    }

    @Test
    void deleteBets(){
        final ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body("Aposta excluída");
        final ResponseEntity<String> actualResponse = betOperationsController.deleteBet(IDT_BET);

        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(betService).deleteBet(anyLong());
    }
}
