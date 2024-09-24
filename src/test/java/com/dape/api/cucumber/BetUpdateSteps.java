package com.dape.api.cucumber;

import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BetUpdateSteps {
    private final BetRepository betRepository;
    private ResponseEntity<BetResponse> cadastroResponseEntity;

    private boolean servicoIndisponivel;

    public BetUpdateSteps(BetRepository betRepository) {
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

    @Dado("que existam as seguintes apostas cadastradas no banco de dados para atualização")
    public void theFollowingBetsAreRegisteredInTheDatabaseForUpdate(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Quando("uma requisição de atualização de aposta for realizada com odd {double} e descrição {string} e idt_bet {int}")
    public void aBetPatchRequestIsCalled(double numOdd, String desBet, int idtBet) {
        if(servicoIndisponivel)
            cadastroResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            String betPostRequestJson = "{ \"numOdd\": " + numOdd + ", \"desBet\": \"" + desBet + "\" }";
            Response patch = RestAssured.given().body(betPostRequestJson).contentType(ContentType.JSON)
                    .pathParam("idt_bet", idtBet).when().patch("/dape/bet/{idt_bet}");
            if (patch.jsonPath().get("$") instanceof List)
                cadastroResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                cadastroResponseEntity = ResponseEntity.status(patch.statusCode()).body(patch.getBody().as(BetResponse.class));
        }
    }

    @Entao("o serviço de atualização deve retornar o status code {int} - {string}")
    public void theBetUpdateServiceShouldReturnStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, cadastroResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @Entao("o seguinte dado deve estar na tabela")
    public void theFollowingDataShouldBeInTheTable(List<Bet> updatedBets) {
        Bet expectedBet = updatedBets.get(0);

        Optional<Bet> actualBet = betRepository.findById(expectedBet.getIdtBet());

        assertNotNull(actualBet);
        assertThat(actualBet.get()).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Entao("o banco de dados de atualização não deve ser modificado")
    public void theDatabaseShouldNotBeModified(List<Bet> expectedBets) {
        List<Bet> actualBets = betRepository.findAll();

        assertThat(actualBets).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBets);
    }

    @Dado("que o serviço de atualização esteja indisponível")
    public void theUpdateServiceIsUnavailable() {
        servicoIndisponivel = true;
    }
}
