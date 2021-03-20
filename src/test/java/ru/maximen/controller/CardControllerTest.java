package ru.maximen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.maximen.entity.Card;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user1", password = "1", roles = "USER")
    void addNewCard() throws Exception {

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