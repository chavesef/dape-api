package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetPostException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BetServiceTest {

    private BetRepository betRepository;
    private BetService betService;

    @BeforeEach
    public void setUp() {
        betRepository = Mockito.mock(BetRepository.class);
    }

    @Test
    void cadastrarAposta() {
        BetPostRequest betPostRequest = new BetPostRequest(
                new BigDecimal("2.12"), "Vitória do River Plate");
        final Bet betEsperada = apostaEsperada(betPostRequest);
        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(betEsperada);

        betService = new BetService(betRepository);
        final BetPostResponse betCriada = betService.cadastrarAposta(betPostRequest);

        assertEquals(betEsperada.getDesBet(), betCriada.getDesBet());
    }

    @Test
    void cadastrarApostaOddInvalida(){
        BetPostRequest betPostRequest = new BetPostRequest(
                BigDecimal.valueOf(-2.12), "Vitória do River Plate");

        betService = new BetService(betRepository);
        BetPostException betPostException = assertThrows(
                BetPostException.class, () -> betService.cadastrarAposta(betPostRequest));

        assertEquals("Valor da odd deve ser maior que 1",
                betPostException.getMessage());
    }

    @Test
    void cadastrarApostaDescricaoInvalida(){
        BetPostRequest betPostRequest = new BetPostRequest(
                new BigDecimal("2.12"), " ");

        betService = new BetService(betRepository);
        BetPostException betPostException = assertThrows(
                BetPostException.class, () -> betService.cadastrarAposta(betPostRequest));

        assertEquals("Descrição da aposta não deve ser nula/vazia",
                betPostException.getMessage());
    }

    private Bet apostaEsperada(BetPostRequest betPostRequest) {
        Bet bet = new Bet();
        bet.setDesBet(betPostRequest.getDesBet());
        bet.setNumOdd(betPostRequest.getNumOdd());
        bet.setBetStatusEnum(BetStatusEnum.PENDING);
        bet.setDatCreated(LocalDateTime.now());
        bet.setDatUpdated(LocalDateTime.now());
        bet.setFlgSelected(0);

        return bet;
    }
}
