package ru.maximen.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximen.dao.TransactionDao;
import ru.maximen.entity.Transaction;
import ru.maximen.services.TransactionService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;

    @Override
    @Transactional
    public List<Transaction> GetTransactionHistory(String CardNumber, Date startDate, Date endDate){
        return transactionDao.loadHistory(CardNumber,startDate,endDate);
    }


}
