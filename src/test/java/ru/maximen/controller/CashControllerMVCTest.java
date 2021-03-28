package ru.maximen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.services.CardService;
import ru.maximen.services.TransactionService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
class CashControllerMVCTest {


    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvcl;

    @Mock
    private CardService cardService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private CashController cashController;

    private AutoCloseable closeable;

    @BeforeEach
    private void init(){
        closeable = MockitoAnnotations.openMocks(this);
        mockMvcl = MockMvcBuilders.standaloneSetup(cashController).build();
    }

    @Test
    void checkBalance() throws Exception {
        mockMvcl.perform(MockMvcRequestBuilders.get("/money/get/1234123412341234"))
                .andExpect(status().isOk());
    }

    @Test
    void loadMoney() throws Exception {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("1234123412341234", 100.0f);

        mockMvcl.perform(MockMvcRequestBuilders.post("/money/add", actionMoneyDto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actionMoneyDto)))
                .andExpect(status().isOk());
    }

    @Test
    void withdrawMoney() throws Exception {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("1234123412341234", 100.0f);


        mockMvcl.perform(MockMvcRequestBuilders.post("/money/withdraw", actionMoneyDto)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(actionMoneyDto)))
                .andExpect(status().isOk());
    }

    @Test
    void transferMoney() throws Exception {
        TransferMoneyDto transferMoneyDto = new TransferMoneyDto("1234123412341234","1234123412341235",100.0f);

        mockMvcl.perform(MockMvcRequestBuilders.post("/money/transfer", transferMoneyDto)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(transferMoneyDto)))
                .andExpect(status().isOk());


    }
}