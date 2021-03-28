package ru.maximen.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximen.entity.Transaction;
import ru.maximen.repository.TransactionRepository;
import ru.maximen.services.TransactionService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public List<Transaction> GetTransactionHistory(String CardNumber, Date startDate, Date endDate){
        return transactionRepository.findByCardNumberAndDateBetween(CardNumber, startDate, endDate);
    }


}
