package ru.maximen.dao.Impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.maximen.dao.TransactionDao;
import ru.maximen.entity.Transaction;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionDaoImpl implements TransactionDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Transaction transaction){
        sessionFactory.getCurrentSession().save(transaction);
    }

    @Override
    public List<Transaction> loadAll(){
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select * from transactions", Transaction.class)
                .getResultList();
    }

    @Override
    public List<Transaction> loadHistory(String cardNumber, Date startDate, Date endDate){
        return sessionFactory
                .getCurrentSession()
                .createNativeQuery("select * from transactions where cardnumber = :cardNumber AND date > :startDate AND date < :endDate", Transaction.class)
                .setParameter("cardNumber", cardNumber)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();
    }



}
