package ru.maximen.services;

import ru.maximen.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    List<Transaction> GetTransactionHistory(String CardNumber, Date startDate, Date endDate);


}
