package ru.maximen.dao;

import ru.maximen.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionDao {

    void save(Transaction transaction);

    List<Transaction> loadAll();

    List<Transaction> loadHistory(String cardNumber, Date startDate, Date endDate);

}
