package ru.maximen.dao.Impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.maximen.dao.CardDao;
import ru.maximen.entity.Card;


@Repository
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao {

    private final SessionFactory sessionFactory;

    @Override
    public void addCard(Card card){

        sessionFactory.getCurrentSession().save(card);
    }

    @Override
    public void deleteCard(String cardNumber){
        sessionFactory.getCurrentSession()
                .createNativeQuery("delete from cards WHERE cardnumber = :cardnumber")
                .setParameter("cardnumber", cardNumber)
                .executeUpdate();;
    }

    @Override
    public Card getCardByCardNumber(String cardNumber){
        try {
            return sessionFactory.getCurrentSession()
                    .createNativeQuery("select * from cards where cardnumber = :cardnumber", Card.class)
                    .setParameter("cardnumber", cardNumber)
                    .getResultList().get(0);
        } catch (IndexOutOfBoundsException q) {
            return null;
        }
    }

    @Override
    public void updateCard(Card card){
        sessionFactory.getCurrentSession().update(card);
    }



}
