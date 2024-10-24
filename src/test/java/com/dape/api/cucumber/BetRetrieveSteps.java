package com.dape.api.cucumber;

import com.dape.api.adapter.dto.response.BetListResponse;
import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BetRetrieveSteps {
    private final BetRepository betRepository;
    private ResponseEntity<BetListResponse> registrationResponseEntity;
    private final Map<String, String> params = new HashMap<>();
    private static final Integer DEFAULT_PAGE = 0;

    private boolean serviceUnavailable;

    public BetRetrieveSteps(BetRepository betRepository) {
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
        params.clear();
    }

    @Dado("que as seguintes apostas estejam cadastradas no banco de dados")
    public void theFollowingBetsAreRegisteredInTheDatabase(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Dado("que uma requisição de consulta com os parametros page {int} seja realizada")
    public void aBetGetRequestIsCalled(int page) {
        aBetGetRequestIsCalledWithParameter("page", String.valueOf(page));
    }

    @Então("devem ser retornados {int} registros para a página {int}")
    public void shouldReturnTheValues(int size, int page) {
        final List<Bet> actualBets = generateBetList(page);
        assertEquals(page, registrationResponseEntity.getBody().getPage());
        assertEquals(size, actualBets.size());
    }

    @Então("o tamanho total da lista deve ser {int}")
    public void theTotalSizeShouldBe(int totalSize) {
        assertEquals(totalSize, registrationResponseEntity.getBody().getTotalElements());
    }

    @Então("o total de páginas deve ser {int}")
    public void theTotalPagesShouldBe(int totalPages) {
        assertEquals(totalPages, registrationResponseEntity.getBody().getTotalPages());
    }

    @Quando("uma requisição de consulta de apostas for realizada")
    public void aBetGetRequestIsCalled() {
        registrationResponseEntity = generateResponseEntityForTheGetRequest();
    }

    @Entao("o serviço de listagem de apostas deve retornar o status code {int} - {string}")
    public void theBetRetrieveServiceShouldReturnStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, registrationResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @Então("os seguintes dados devem ser retornados para a página {int}")
    public void theFollowingDataShouldBeReturnedToPage(int page, List<Bet> expectedBets) {
        aBetGetRequestIsCalledWithParameter("page", String.valueOf(page));

        final List<Bet> actualBets = generateBetList(page);

        assertEquals(expectedBets.size(), actualBets.size());
        assertThat(actualBets).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBets);
    }

    @Quando("uma requisição de consulta de apostas for realizada com o parâmetro {string} valor {string}")
    public void aBetGetRequestIsCalledWithParameter(String parameter, String value) {
        params.put(parameter, value);
        aBetGetRequestIsCalled();
    }

    @Quando("uma requisição de consulta de apostas for realizada com os parâmetros {string} valor {string} e {string} valor {string}")
    public void aBetGetRequestIsCalledWithParameters(String firstParameter, String firstValue, String secondParameter, String secondValue) {
        params.put(firstParameter, firstValue);
        params.put(secondParameter, secondValue);
        aBetGetRequestIsCalled();
    }

    @Então("o conteúdo de resposta deve ser vazio")
    public void theResponseShouldBeEmpty() {
        final List<Bet> actualBets = generateBetList(DEFAULT_PAGE);
        assertThat(actualBets).isEmpty();
    }

    @Dado("que o serviço de busca de apostas esteja indisponível")
    public void theServiceIsUnavailable() {
        serviceUnavailable = true;
    }

    private ResponseEntity<BetListResponse> generateResponseEntityForTheGetRequest() {
        if(serviceUnavailable)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        System.out.println(params);
        final Response getResponse = given().params(params).when()
                .get("/dape/bet").then().extract().response();

        if (getResponse.getStatusCode() == HttpStatus.OK.value()) {
            return new ResponseEntity<>(getResponse.as(BetListResponse.class), HttpStatus.valueOf(getResponse.getStatusCode()));
        }
        return new ResponseEntity<>(HttpStatus.valueOf(getResponse.getStatusCode()));
    }

    private List<Bet> generateBetList (Integer page) {
        try {
            List<Bet> bets = new ArrayList<>();
            int size = registrationResponseEntity.getBody().getSize();
            int totalElements = Math.toIntExact(registrationResponseEntity.getBody().getTotalElements());
            int endIndex = Math.min(size, totalElements - page*size);

            for (int i = 0; i < endIndex; i++) {
                Bet bet = getBetFromResponseEntity(i);
                bets.add(bet);
            }
            return bets;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private Bet getBetFromResponseEntity(int i) {
        Bet bet = new Bet();
        BetResponse betResponse = Objects.requireNonNull(registrationResponseEntity.getBody()).getBetResponseList().get(i);
        bet.setBetStatusEnum(betResponse.getBetStatusEnum());
        bet.setDesBet(betResponse.getDesBet());
        bet.setIdtBet(betResponse.getIdtBet());
        bet.setFlgSelected(betResponse.getFlgSelected());
        bet.setNumOdd(betResponse.getNumOdd());
        bet.setDatCreated(betResponse.getDatCreated());
        bet.setDatUpdated(betResponse.getDatUpdated());
        return bet;
    }
}
