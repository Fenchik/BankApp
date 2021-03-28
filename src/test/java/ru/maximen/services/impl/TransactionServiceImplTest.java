package ru.maximen.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maximen.entity.Transaction;
import ru.maximen.repository.TransactionRepository;
import ru.maximen.services.TransactionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void getTransactionHistory() {

        List<Transaction> transactionList = new ArrayList<>();

        String cardNumber = "123412341234";
        Date startDate = new Date(2021-03-20-15-30);
        Date endDate = new Date(2022-03-2-0-18-53);

        when(transactionRepository.findByCardNumberAndDateBetween(cardNumber,startDate,endDate)).thenReturn(transactionList);

        assertThat(transactionService.GetTransactionHistory(cardNumber,startDate,endDate)).isEqualTo(transactionList);
    }
}