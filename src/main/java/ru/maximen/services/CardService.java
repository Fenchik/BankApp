package ru.maximen.services;

import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.CardDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.entity.Card;

public interface CardService {

    String addCard(CardDto cardDto);

    void deleteCard(String cardNumber);

    Float getBalance(String cardNumber);

    String loadMoney(ActionMoneyDto actionMoneyDto);

    String withdrawMoney(ActionMoneyDto actionMoneyDto);

    String transferMoney(TransferMoneyDto transferMoneyDto);
}
