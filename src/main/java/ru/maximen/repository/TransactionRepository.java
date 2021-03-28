package ru.maximen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maximen.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCardNumberAndDateBetween(String cardNumber, Date startDate, Date endDate);
}
