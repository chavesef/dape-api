package com.dape.api.cucumber;

import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.dto.response.TicketResponse;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.adapter.repository.ClientRepository;
import com.dape.api.adapter.repository.TicketBetRepository;
import com.dape.api.adapter.repository.TicketRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.entity.TicketBet;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketRegistrationSteps {
    private final TicketRepository ticketRepository;
    private final TicketBetRepository ticketBetRepository;
    private final BetRepository betRepository;
    private final ClientRepository clientRepository;
    private ResponseEntity<TicketResponse> registrationResponseEntity;

    private boolean serviceUnavailable;

    public TicketRegistrationSteps(TicketRepository ticketRepository, TicketBetRepository ticketBetRepository, BetRepository betRepository, ClientRepository clientRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketBetRepository = ticketBetRepository;
        this.betRepository = betRepository;
        this.clientRepository = clientRepository;
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

    @Dado("que existam as seguintes apostas cadastradas no banco de dados para criar bilhetes")
    public void queExistamAsSeguintesApostasCadastradasNoBancoDeDadosParaCriarBilhetes(List<Bet> bets) {
        betRepository.saveAll(bets);
    }

    @Dado("os seguintes clientes cadastrados no banco de dados")
    public void osSeguintesClientesCadastradosNoBancoDeDados(List<Client> clients) {
        clientRepository.saveAll(clients);
    }

    @Quando("uma requisição de criação de bilhetes for realizada com num_ammount {double} e idt_client {int} e idt_bet [{int}]")
    public void umaRequisiçãoDeCriaçãoDeBilhetesForRealizadaComNum_ammountEIdt_clientEIdt_bet(double numAmmount, int idtClient, int idtBet) {
        registrationResponseEntity = generateResponseEntityForThePostRequest(numAmmount, idtClient, List.of((long) idtBet));
    }

    @Entao("o serviço de cadastro de bilhetes deve retornar o status code {int} - {string}")
    public void oServiçoDeCadastroDeBilhetesDeveRetornarOStatusCode(int expectedStatusCode, String expectedCodeDescription) {
        assertEquals(expectedStatusCode, registrationResponseEntity.getStatusCode().value());
        assertEquals(expectedCodeDescription, HttpStatus.valueOf(expectedStatusCode).getReasonPhrase());
    }

    @Entao("o seguinte bilhete deve ser cadastrado no banco de dados")
    public void oSeguinteBilheteDeveSerCadastradoNoBancoDeDados(List<Ticket> tickets) {
        final List<Ticket> actualTickets = ticketRepository.findAll();
        final Ticket ticket = tickets.get(0);
        assertEquals(ticket.getIdtTicket(), actualTickets.get(actualTickets.size()-1).getIdtTicket());
    }

    @Entao("o seguinte dado deve ser cadastrado na tabela ticket_bet")
    public void oSeguinteDadoDeveSerCadastradoNaTabelaTicket_bet(List<TicketBet> ticketBets) {
        final List<TicketBet> actualTicketBets = ticketBetRepository.findAll();
        final TicketBet ticketBet = ticketBets.get(0);
        assertEquals(ticketBet.getIdtTicketBet(), actualTicketBets.get(actualTicketBets.size()-1).getIdtTicketBet());
    }

    @Quando("uma requisição de criação de bilhetes for realizada com num_ammount {double} e idt_client {int} e idt_bet [{int}, {int}, {int}]")
    public void umaRequisiçãoDeCriaçãoDeBilhetesForRealizadaComNum_ammountEIdt_clientEIdt_bet(double numAmmount, int idtClient, int idtBetOne, int idtBetTwo, int idtBetThree) {
        registrationResponseEntity = generateResponseEntityForThePostRequest(numAmmount, idtClient, List.of((long) idtBetOne, (long) idtBetTwo, (long) idtBetThree));
    }

    @Entao("os seguintes dados devem ser cadastrados na tabela ticket_bet")
    public void osSeguintesDadosDevemSerCadastradosNaTabelaTicket_bet(List<TicketBet> ticketBets) {
        final List<TicketBet> actualTicketBets = ticketBetRepository.findAll();

        assertEquals(ticketBets.get(0).getIdtTicketBet(), actualTicketBets.get(actualTicketBets.size()-3).getIdtTicketBet());
        assertEquals(ticketBets.get(1).getIdtTicketBet(), actualTicketBets.get(actualTicketBets.size()-2).getIdtTicketBet());
        assertEquals(ticketBets.get(2).getIdtTicketBet(), actualTicketBets.get(actualTicketBets.size()-1).getIdtTicketBet());
    }

    @Entao("o bilhete não deve ser cadastrado")
    public void oBilheteNãoDeveSerCadastrado() {
        assertEquals(0, ticketRepository.findAll().size());
    }

    @Dado("que o serviço de cadastro esteja indisponível")
    public void queOServiçoDeCadastroEstejaIndisponível() {
        serviceUnavailable = true;
    }

    private ResponseEntity<TicketResponse> generateResponseEntityForThePostRequest(double numAmmount, int idtClient, List<Long> idtBets) {
        if(serviceUnavailable)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        final TicketRequest ticketRequest= new TicketRequest(BigDecimal.valueOf(numAmmount), (long) idtClient, idtBets);

        final Response postResponse = given().body(ticketRequest).contentType(ContentType.JSON).when()
                .post("/dape/ticket").then().extract().response();

        if (postResponse.getStatusCode() == HttpStatus.CREATED.value()) {
            return new ResponseEntity<>(postResponse.as(TicketResponse.class), HttpStatus.valueOf(postResponse.getStatusCode()));
        }
        return new ResponseEntity<>(HttpStatus.valueOf(postResponse.getStatusCode()));
    }
}
