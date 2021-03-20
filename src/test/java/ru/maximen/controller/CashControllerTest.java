package ru.maximen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.maximen.dao.CardDao;
import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.services.CardService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CashControllerTest {

    @Autowired
    private MockMvc mockMvcl;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "user1", password = "1", roles = "USER")
    void checkBalance() throws Exception {
        mockMvcl.perform(MockMvcRequestBuilders.get("/money/get/1234123412341234"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "1", roles = "USER")
    void loadMoney() throws Exception {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("1234123412341234", 100.0f);

        mockMvcl.perform(MockMvcRequestBuilders.post("/money/add", actionMoneyDto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actionMoneyDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "1", roles = "USER")
    void withdrawMoney() throws Exception {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("1234123412341234", 100.0f);


        mockMvcl.perform(MockMvcRequestBuilders.post("/money/withdraw", actionMoneyDto)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(actionMoneyDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "1", roles = "USER")
    void transferMoney() throws Exception {
        TransferMoneyDto transferMoneyDto = new TransferMoneyDto("1234123412341234","1234123412341235",100.0f);

        mockMvcl.perform(MockMvcRequestBuilders.post("/money/transfer", transferMoneyDto)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(transferMoneyDto)))
                .andExpect(status().isOk());

    }
}