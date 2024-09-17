package com.dape.api.cucumber;

import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BetRegistrationSteps {
    private final BetRepository betRepository;
    private ResponseEntity<BetPostResponse> cadastroResponseEntity;

    private boolean servicoIndisponivel;

    public BetRegistrationSteps(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @BeforeAll
    public static void setUp() {
        baseURI = "http://localhost";
        port = 8080;
        System.setProperty("port", String.valueOf(port));
    }

    @BeforeEach
    public void serviceAvailable() {
        servicoIndisponivel = false;
    }

    @Dado("que existam as seguintes apostas cadastradas no banco de dados")
    public void theFollowingBetsAreRegisteredInTheDatabase(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Quando("uma requisição de criação de aposta for realizada com odd {double} e descrição {string}")
    public void aBetPostRequestIsCalled(double numOdd, String desBet) {
        if(servicoIndisponivel)
            cadastroResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            String betPostRequestJson = "{ \"numOdd\": " + numOdd + ", \"desBet\": \"" + desBet + "\" }";
            Response post = given().body(betPostRequestJson).contentType(ContentType.JSON).when()
                    .post("/dape/bet");
            if (post.jsonPath().get("$") instanceof List)
                cadastroResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                cadastroResponseEntity = ResponseEntity.status(post.statusCode()).body(post.getBody().as(BetPostResponse.class));
        }
    }

    @Entao("o serviço de cadastro de apostas deve retornar o status code {int} - {string}")
    public void theBetRegistrationServiceShouldReturnStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, cadastroResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @Entao("os seguintes dados devem ser cadastrados no banco de dados")
    public void theFollowingDataShouldBeRegisteredInTheDatabase(List<Bet> bets) {
        List<Bet> actualBets = betRepository.findAll();
        Bet bet = bets.get(0);
        assertEquals(bet.getDesBet(), actualBets.get(actualBets.size()-1).getDesBet());
    }

    @Entao("o banco de dados não deve ser modificado")
    public void theDatabaseShouldNotBeModified(List<Bet> bets) {
        List<Bet> actualBets = betRepository.findAll();
        assertEquals(bets.size(), actualBets.size());
    }

    @Dado("que o serviço esteja indisponível")
    public void theServiceIsUnavailable() {
        servicoIndisponivel = true;
    }
}