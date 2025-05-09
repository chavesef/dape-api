package com.dape.api.cucumber;

import com.dape.api.adapter.dto.response.TicketListResponse;
import com.dape.api.adapter.dto.response.TicketResponse;
import com.dape.api.adapter.repository.TicketRepository;
import com.dape.api.domain.entity.Ticket;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class TicketRetrieveSteps {

    private final TicketRepository ticketRepository;
    private ResponseEntity<TicketListResponse> registrationResponseEntity;
    private final Map<String, String> params = new HashMap<>();
    private static final Integer DEFAULT_PAGE = 0;

    private boolean serviceUnavailable;

    public TicketRetrieveSteps(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @BeforeAll
    static void setUp() {
        baseURI = "http://localhost";
        port = 8080;
        System.setProperty("port", String.valueOf(port));
    }

    @BeforeEach
    void serviceAvailable() {
        serviceUnavailable = false;
        params.clear();
    }

    @Dado("que existam os seguintes bilhetes cadastrados no banco de dados")
    public void theFollowingTicketsAreRegisteredInTheDatabase(List<Ticket> tickets) {
        ticketRepository.saveAll(tickets);
    }

    @Dado("que o serviço de consulta de bilhetes esteja indisponível")
    public void theServiceIsUnavailable() {
        serviceUnavailable = true;
    }

    @Quando("uma requisição de consulta de bilhetes for realizada")
    public void aTicketGetRequestIsCalled() {
        registrationResponseEntity = generateResponseEntityForTheGetRequest();
    }

    @Entao("o serviço de listagem de bilhetes deve retornar o status code {int} - {string}")
    public void theTicketRetrieveServiceShouldReturnStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, registrationResponseEntity.getStatusCode().value());
        assertEquals(HttpStatus.valueOf(expectedStatusCode).getReasonPhrase(), expectedCodeDescription);
    }


    @Entao("os seguintes bilhetes devem ser retornados para a página {int}")
    public void theFollowingTicketShouldBeReturnedToPage(int page, List<Ticket> expectedTickets) {
        aTicketGetRequestIsCalledWithParameter("page", String.valueOf(page));

        final List<Ticket> actualTickets = generateTicketList(page);

        assertEquals(expectedTickets.size(), actualTickets.size());
        assertThat(actualTickets).usingRecursiveComparison().ignoringFields("client", "datUpdated").isEqualTo(expectedTickets);
    }

    @Quando("uma requisição de consulta de bilhetes for realizada com o parâmetro {string} valor {string}")
    public void aTicketGetRequestIsCalledWithParameter(String parameter, String value) {
        params.put(parameter, value);
        aTicketGetRequestIsCalled();
    }


    @Quando("uma requisição de consulta de bilhetes for realizada com os parâmetros {string} valor {string} e {string} valor {string}")
    public void aTicketGetRequestIsCalledWithParameters(String firstParameter, String firstValue, String secondParameter, String secondValue) {
        params.put(firstParameter, firstValue);
        params.put(secondParameter, secondValue);
        aTicketGetRequestIsCalled();
    }


    @Dado("que uma requisição de consulta de bilhetes com os parametros page {int} seja realizada")
    public void aTicketGetRequestIsCalledWithPageParameter(int page) {
        aTicketGetRequestIsCalledWithParameter("page", String.valueOf(page));
    }

    @Entao("devem ser retornados {int} bilhetes para a página {int}")
    public void shouldReturnTheValues(int size, int page) {
        final List<Ticket> actualTickets = generateTicketList(page);
        assertEquals(page, registrationResponseEntity.getBody().getPage());
        assertEquals(size, actualTickets.size());
    }

    @Entao("o numero total de bilhetes deve ser {int}")
    public void theTotalSizeShouldBe(int totalSize) {
        assertEquals(totalSize, registrationResponseEntity.getBody().getTotalElements());
    }

    @Entao("o total de páginas de bilhetes deve ser {int}")
    public void theTotalPagesShouldBe(int totalPages) {
        assertEquals(totalPages, registrationResponseEntity.getBody().getTotalPages());
    }

    @Entao("o conteúdo de resposta de bilhetes deve ser vazio")
    public void theResponseShouldBeEmpty() {
        final List<Ticket> actualTickets = generateTicketList(DEFAULT_PAGE);
        assertThat(actualTickets).isEmpty();
    }

    private ResponseEntity<TicketListResponse> generateResponseEntityForTheGetRequest() {
        if(serviceUnavailable)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        final Response getResponse = given().params(params).when()
                .get("/dape/ticket").then().extract().response();

        if (getResponse.getStatusCode() == HttpStatus.OK.value()) {
            return new ResponseEntity<>(getResponse.as(TicketListResponse.class), HttpStatus.valueOf(getResponse.getStatusCode()));
        }
        return new ResponseEntity<>(HttpStatus.valueOf(getResponse.getStatusCode()));
    }

    private List<Ticket> generateTicketList (Integer page) {
        try {
            List<Ticket> tickets = new ArrayList<>();
            int size = registrationResponseEntity.getBody().getSize();
            int totalElements = Math.toIntExact(registrationResponseEntity.getBody().getTotalElements());
            int endIndex = Math.min(size, totalElements - page*size);

            for (int i = 0; i < endIndex; i++) {
                Ticket ticket = getTicketFromResponseEntity(i);
                tickets.add(ticket);
            }
            return tickets;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private Ticket getTicketFromResponseEntity(int i) {
        Ticket ticket = new Ticket();
        TicketResponse ticketResponse = Objects.requireNonNull(registrationResponseEntity.getBody()).getTicketResponseList().get(i);
        ticket.setTicketStatusEnum(ticketResponse.getTicketStatus());
        ticket.setIdtTicket(ticketResponse.getIdtTicket());
        ticket.setDatCreated(ticketResponse.getDatCreated());
        ticket.setNumAmount(ticketResponse.getNumAmount());
        ticket.setTicketTypeEnum(ticketResponse.getTicketType());
        ticket.setNumFinalOdd(ticketResponse.getNumFinalOdd());
        return ticket;
    }
}
