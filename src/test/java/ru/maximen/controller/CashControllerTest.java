package ru.maximen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.services.CardService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@Slf4j
@ExtendWith(MockitoExtension.class)
class CashControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    @Autowired
    CashController cashController;

    ActionMoneyDto actionMoneyDtoTo;
    ActionMoneyDto actionMoneyDtoFrom;

    @BeforeEach
    public void init(){
        actionMoneyDtoTo = new ActionMoneyDto("123412342134", 150f);
        actionMoneyDtoFrom = new ActionMoneyDto("123412342134", 350f);
    }


    @Test
    void checkBalance(){
        Float balance = 100.5f;
        when(cardService.getBalance(anyString())).thenReturn(balance);
        assertThat(cashController.checkBalance("1234123412341234")).isEqualTo("100.5");
    }


    @Test
    void loadMoney() throws JsonProcessingException {
        when(cardService.loadMoney(actionMoneyDtoTo)).thenReturn(actionMoneyDtoFrom.toString());

        assertThat(cashController.loadMoney(actionMoneyDtoTo)).isEqualTo(actionMoneyDtoFrom.toString());
    }

    @Test
    void withdrawMoney() {
        when(cardService.withdrawMoney(actionMoneyDtoTo)).thenReturn(actionMoneyDtoFrom.toString());

        assertThat(cashController.withdrawMoney(actionMoneyDtoTo)).isEqualTo(actionMoneyDtoFrom.toString());
    }

    @Test
    void transferMoney() {
        TransferMoneyDto transferMoneyDtoTo = new TransferMoneyDto("123412342134","123412342135", 150f);

        when(cardService.transferMoney(transferMoneyDtoTo)).thenReturn("Succsessful!");

        assertThat(cashController.transferMoney(transferMoneyDtoTo)).isEqualTo("Succsessful!");
    }
}