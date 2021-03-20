package ru.maximen.dao;

        import ru.maximen.entity.Card;

public interface CardDao {

    void addCard(Card card);
    void deleteCard(String cardNumber);
    Card getCardByCardNumber(String cardNumber);
    void updateCard(Card card);
}

