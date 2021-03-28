package ru.maximen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maximen.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

    void deleteCardByCardNumber(String cardNumber);

    Card getCardByCardNumber(String cardNumber);

    @Modifying
    @Query(value =
            "update cards Set balance = :balance where card_number = :cardnumber",
            nativeQuery = true)
    void updateCardBalanceByCardNumber(@Param("cardnumber") String cardNumber, @Param("balance") Float balance);
}
