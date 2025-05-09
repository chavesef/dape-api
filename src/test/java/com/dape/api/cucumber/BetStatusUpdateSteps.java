package com.dape.api.cucumber;

import com.dape.api.adapter.dto.UpdateBetEvent;
import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.cucumber.consumer.KafkaUpdateBetEventConsumer;
import com.dape.api.domain.entity.Bet;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class BetStatusUpdateSteps {
    private final BetRepository betRepository;
    private ResponseEntity<BetResponse> statusUpdateResponseEntity;
    private final KafkaUpdateBetEventConsumer kafkaConsumer;

    private boolean serviceUnavailable;

    public BetStatusUpdateSteps(BetRepository betRepository, KafkaUpdateBetEventConsumer kafkaConsumer) {
        this.betRepository = betRepository;
        this.kafkaConsumer = kafkaConsumer;
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

    @Dado("que existam as seguintes apostas cadastradas no banco de dados para atualização de status")
    public void theFollowingBetsAreRegisteredInTheDatabaseForStatusUpdate(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Quando("uma requisição de atualização de aposta for realizada com status {string} e idt_bet {int}")
    public void aBetPatchRequestIsCalled(String betStatus, int idtBet) {
        statusUpdateResponseEntity = generateResponseEntityForThePatchRequest(betStatus, idtBet);
    }

    @Entao("o serviço de atualização de status deve retornar o status code {int} - {string}")
    public void theBetStatusUpdateServiceShouldReturnStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, statusUpdateResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @Entao("o seguinte dado deve estar na tabela de atualização de status de apostas")
    public void theFollowingDataShouldBeInTheTable(List<Bet> updatedBets) {
        final Bet expectedBet = updatedBets.get(0);

        final Optional<Bet> actualBet = betRepository.findById(expectedBet.getIdtBet());

        assertNotNull(actualBet);
        assertThat(actualBet.get()).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Entao("a tabela bet de atualização de status deve conter os registros abaixo")
    public void theDatabaseShouldNotBeModified(List<Bet> expectedBets) {
        final List<Bet> actualBets = betRepository.findAll();

        assertThat(actualBets).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBets);
    }

    @Dado("que o serviço de atualização de status esteja indisponível")
    public void theStatusUpdateServiceIsUnavailable() {
        serviceUnavailable = true;
    }

    private ResponseEntity<BetResponse> generateResponseEntityForThePatchRequest(String betStatus, int idtBet) {
        if (serviceUnavailable)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        final BetStatusRequest betStatusRequest = new BetStatusRequest();

        betStatusRequest.setBetStatus(betStatus);

        final Response patchResponse = given().body(betStatusRequest).contentType(ContentType.JSON)
                .pathParam("idt_bet", idtBet).when().patch("/dape/bet/{idt_bet}/status")
                .then().extract().response();

        if (patchResponse.getStatusCode() == HttpStatus.OK.value()) {
            return new ResponseEntity<>(patchResponse.as(BetResponse.class), HttpStatus.valueOf(patchResponse.getStatusCode()));
        }
        return new ResponseEntity<>(HttpStatus.valueOf(patchResponse.getStatusCode()));
    }

    @Entao("deve ter sido enviada uma mensagem ao Kafka")
    public void aMessageMustHaveBeenSentToKafka(List<UpdateBetEvent> expectedEvents) {
        final List<UpdateBetEvent> actualEvents = kafkaConsumer.consumeUpdateBetEvents();

        assertThat(actualEvents).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedEvents);
    }
}
