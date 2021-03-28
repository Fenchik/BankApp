package ru.maximen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.maximen.entity.Card;
import ru.maximen.services.CardService;
import ru.maximen.services.TransactionService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CardControllerMVCTest {


    private MockMvc mockMvc;

    @Mock
    private CardService cardService;

    @Mock
    private TransactionService transactionService;

    private AutoCloseable closeable;

    @InjectMocks
    private CardController cardController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void init(){
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @AfterEach
    private void close() throws Exception {
        closeable.close();
    }



    @Test
    void addNewCardIsOk() throws Exception {


        Card card = new Card(
                "1001000003322132",
                1443l,
                240.5f,
                "29.10.2024",
                null,
                323l);

        mockMvc.perform(MockMvcRequestBuilders.post("/add", card)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isOk());
    }

    @Test
    void addNewCardIncorrectCardNumber() throws Exception {


        Card card = new Card(
                "100100000332213233333",
                1443l,
                240.5f,
                "29.10.2024",
                null,
                323l);

        mockMvc.perform(MockMvcRequestBuilders.post("/add", card)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user1", password = "1", roles = "USER")
    void deleteCard() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/1234123412341324")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "1", roles = "USER")
    void getTransactionHistory() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/gettranshist/1001000100020004/2021-03-05-17-30/2021-03-06-17-53"))
                .andExpect(status().isOk());
    }
}