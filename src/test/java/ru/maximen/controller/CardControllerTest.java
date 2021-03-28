package ru.maximen.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maximen.dto.CardDto;
import ru.maximen.entity.Transaction;
import ru.maximen.services.CardService;
import ru.maximen.services.TransactionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @Mock
    CardService cardService;

    @Mock
    TransactionService transactionService;

    @InjectMocks
    CardController cardController;



    @Test
    void addNewCard() {
        CardDto cardDto = new CardDto();

        when(cardService.addCard(cardDto)).thenReturn("Card Added!");

        assertThat(cardController.addNewCard(cardDto)).isEqualTo("Card Added!");
    }

    @Test
    void deleteCard() {
        assertThat(cardController.deleteCard(anyString())).isEqualTo("Delete");
    }

    @Test
    void getTransactionHistory() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transactionList.add(transaction);

        String cardNumber = "1001000003322132";

        Date dateStart = new Date(2021-03-20-15-30);
        Date dateEnd = new Date(2021-03-20-15-31);

        when(transactionService.GetTransactionHistory(cardNumber,dateStart,dateEnd)).thenReturn(transactionList);

        assertThat(cardController.getTransactionHistory(cardNumber,dateStart,dateEnd)).isEqualTo(transactionList);
    }
}