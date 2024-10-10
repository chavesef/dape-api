package com.dape.api.cucumber;

import com.dape.api.adapter.dto.request.BetRequest;
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

import java.math.BigDecimal;
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
    private ResponseEntity<BetResponse> updateResponseEntity;

    private boolean serviceUnavailable;

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
        serviceUnavailable = false;
    }

    @Dado("que existam as seguintes apostas cadastradas no banco de dados para atualização")
    public void theFollowingBetsAreRegisteredInTheDatabaseForUpdate(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Quando("uma requisição de atualização de aposta for realizada com odd {double} e descrição {string} e idt_bet {int}")
    public void aBetPatchRequestIsCalled(double numOdd, String desBet, int idtBet) {
        updateResponseEntity = generateResponseEntityForThePatchRequest(numOdd, desBet, idtBet);
    }

    @Entao("o serviço de atualização deve retornar o status code {int} - {string}")
    public void theBetUpdateServiceShouldReturnStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, updateResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @Entao("o seguinte dado deve estar na tabela")
    public void theFollowingDataShouldBeInTheTable(List<Bet> updatedBets) {
        final Bet expectedBet = updatedBets.get(0);

        final Optional<Bet> actualBet = betRepository.findById(expectedBet.getIdtBet());

        assertNotNull(actualBet);
        assertThat(actualBet.get()).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Entao("o banco de dados de atualização não deve ser modificado")
    public void theDatabaseShouldNotBeModified(List<Bet> expectedBets) {
        final List<Bet> actualBets = betRepository.findAll();

        assertThat(actualBets).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBets);
    }

    @Dado("que o serviço de atualização esteja indisponível")
    public void theUpdateServiceIsUnavailable() {
        serviceUnavailable = true;
    }

    private ResponseEntity<BetResponse> generateResponseEntityForThePatchRequest(double numOdd, String desBet, int idtBet) {
        if(serviceUnavailable)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        final BetRequest betRequest = new BetRequest(new BigDecimal(numOdd), desBet);

        Response patchResponse = RestAssured.given().body(betRequest).contentType(ContentType.JSON)
                .pathParam("idt_bet", idtBet).when().patch("/dape/bet/{idt_bet}")
                .then().extract().response();

        if (patchResponse.getStatusCode() == HttpStatus.OK.value()) {
            return new ResponseEntity<>(patchResponse.as(BetResponse.class), HttpStatus.valueOf(patchResponse.getStatusCode()));
        }
        return new ResponseEntity<>(HttpStatus.valueOf(patchResponse.getStatusCode()));
    }
}
