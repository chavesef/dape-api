package com.dape.api.cucumber;

import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CadastroApostasSteps {
    private final BetRepository betRepository;
    private ResponseEntity<Object> cadastroResponseEntity;

    private boolean servicoIndisponivel;

    public CadastroApostasSteps(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @BeforeEach
    public void setUp() {
        servicoIndisponivel = false;
    }

    @Dado("que existam as seguintes apostas cadastradas no banco de dados")
    public void queExistamAsSeguintesApostasCadastradasNoBancoDeDados(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Quando("uma requisição de criação de aposta for realizada com odd {double} e descrição {string}")
    public void umaRequisiçãoDeCriaçãoDeApostaForRealizadaComOddEDescrição(double numOdd, String desBet) {
        if(servicoIndisponivel)
            cadastroResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            if (numOdd <= 1)
                cadastroResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else if (desBet.isBlank())
                cadastroResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else {
                Bet newBet = new Bet();
                newBet.setDesBet(desBet);
                newBet.setNumOdd(new BigDecimal(numOdd));
                newBet.setFlgSelected(0);
                newBet.setDatCreated(LocalDateTime.now());
                newBet.setDatUpdated(LocalDateTime.now());
                newBet.setBetStatusEnum(BetStatusEnum.PENDING);

                Bet savedBet = betRepository.save(newBet);
                cadastroResponseEntity = new ResponseEntity<>(savedBet, HttpStatus.CREATED);
            }

        }
    }

    @Entao("o serviço de cadastro de apostas deve retornar o status code {int} - {string}")
    public void oServiçoDeCadastroDeApostasDeveRetornarOStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, cadastroResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @E("os seguintes dados devem ser cadastrados no banco de dados")
    public void osSeguintesDadosDevemSerCadastradosNoBancoDeDados(List<Bet> bets) {
        List<Bet> actualBets = betRepository.findAll();
        Bet bet = bets.get(0);
        assertEquals(bet.getDesBet(), actualBets.get(actualBets.size()-1).getDesBet());
    }

    @E("o banco de dados deve se manter")
    public void oBancoDeDadosDeveSeManter(List<Bet> bets) {
        List<Bet> actualBets = betRepository.findAll();

        assertEquals(bets.size(), actualBets.size());
    }

    @Dado("que o serviço esteja indisponível")
    public void queOServiçoEstejaIndisponível() {
        servicoIndisponivel = true;
    }
}
