package com.dape.api.cucumber;

import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BetDeletionSteps {
    private final BetRepository betRepository;
    private ResponseEntity<String> registrationResponseEntity;

    private boolean serviceUnavailable;

    public BetDeletionSteps(BetRepository betRepository) {
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

    @Dado("que existam as seguintes apostas cadastradas no banco de dados para exclusão")
    public void queExistamAsSeguintesApostasCadastradasNoBancoDeDadosParaExclusão(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Quando("uma requisição de remoção de aposta for realizada com idt_bet {int}")
    public void umaRequisiçãoDeRemoçãoDeApostaForRealizadaComOParâmetroValor(int idtBet) {
        registrationResponseEntity = generateResponseEntityForTheDeleteRequest(idtBet);
    }

    @Entao("o serviço de remoção deve retornar o status code {int} - {string}")
    public void oServiçoDeRemoçãoDeveRetornarOStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, registrationResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @Entao("o banco de dados de conter os seguintes registros")
    public void oSeguinteDadoDeveSerDeletadoDaTabela(List<Bet> expectedBets) {
        final List<Bet> actualBets = betRepository.findAll();

        assertThat(actualBets).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBets);
    }

    @Dado("que o serviço de exclusão esteja indisponível")
    public void theDeleteServiceIsUnavailable() {
        serviceUnavailable = true;
    }

    private ResponseEntity<String> generateResponseEntityForTheDeleteRequest(int idtBet) {
        if(serviceUnavailable)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        final Response deleteResponse = given().pathParam("idt_bet", idtBet).when().delete("/dape/bet/{idt_bet}")
                .then().extract().response();
        System.out.println(deleteResponse.body());
        System.out.println(deleteResponse.asString());
        System.out.println(deleteResponse.getStatusCode());

        if (deleteResponse.getStatusCode() == HttpStatus.OK.value()) {
            return new ResponseEntity<>(deleteResponse.asString(), HttpStatus.valueOf(deleteResponse.getStatusCode()));
        }
        return new ResponseEntity<>(HttpStatus.valueOf(deleteResponse.getStatusCode()));
    }
}
