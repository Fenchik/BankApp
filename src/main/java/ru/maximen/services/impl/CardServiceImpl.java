package ru.maximen.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximen.converter.CardConverter;
import ru.maximen.dao.CardDao;
import ru.maximen.dao.TransactionDao;
import ru.maximen.dao.UserDao;
import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.CardDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.entity.Card;
import ru.maximen.entity.Transaction;
import ru.maximen.services.CardService;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;
    private final TransactionDao transactionDao;
    private final UserDao userDao;

    private final CardConverter cardConverter;

    @Override
    @Transactional
    public String addCard(CardDto cardDto){

        Card card = cardConverter.DtoToCard(cardDto);

        log.info("add card");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        card.setUser(userDao.findByLogin(authentication.getName()));


        if (card.getCardNumber().length() != 16) {
            log.info("Incorrect card number");
            return "Incorrect card number";
        }

        if ((cardDao.getCardByCardNumber(card.getCardNumber()) == null)
                ||!(card.getCardNumber().equals(cardDao.getCardByCardNumber(card.getCardNumber()).getCardNumber()))) {
            cardDao.addCard(card);
            return "Card added";
        }

        log.info("Such a card already exists");
        return "Such a card already exists";
    }

    @Override
    @Transactional
    public void deleteCard(String cardNumber){
        cardDao.deleteCard(cardNumber);
    }

    @Override
    @Transactional
    public Float getBalance(String cardNumber){
        return cardDao.getCardByCardNumber(cardNumber).getBalance();
    }

    @Override
    @Transactional
    public String loadMoney(ActionMoneyDto actionMoneyDto){

        String cardNumber = actionMoneyDto.getCardNumber();

        Card card = cardDao.getCardByCardNumber(cardNumber);

        if (card == null) {
            return "Card ("+ actionMoneyDto.getCardNumber() + ") not found";
        }


        Float amount = actionMoneyDto.getAmount();
        Float balance = card.getBalance();

        card.setBalance(balance + amount);
        cardDao.updateCard(card);

        Transaction transaction = new Transaction();
        transaction.setCardNumber(cardNumber);
        transaction.setDate(new Date());
        transaction.setAmount(amount);
        transaction.setBalance(cardDao.getCardByCardNumber(cardNumber).getBalance());
        transaction.setType("load");
            transactionDao.save(transaction);

        return "Successful";
    }

    @Override
    @Transactional
    public String withdrawMoney(ActionMoneyDto actionMoneyDto){
        Card card = cardDao.getCardByCardNumber(actionMoneyDto.getCardNumber());
        if (card == null) {
            return "Card (" + actionMoneyDto.getCardNumber() + ") not found";
        }

        String cardNumber = actionMoneyDto.getCardNumber();
        Float amount = actionMoneyDto.getAmount();
        Float balance = card.getBalance();

        if (balance < amount) {
            return "Insufficient funds";
        } else {
            card.setBalance(balance - amount);
            cardDao.updateCard(card);

            Transaction transaction = new Transaction();
            transaction.setCardNumber(cardNumber);

            transaction.setDate(new Date());
            transaction.setAmount(amount);
            transaction.setBalance(cardDao.getCardByCardNumber(cardNumber).getBalance());
            transaction.setType("withdraw");
            transactionDao.save(transaction);

            return "Successful";
        }
    }

    @Override
    @Transactional
    public String transferMoney(TransferMoneyDto transferMoneyDto){
        //Проверка на возможность сняттия средств и наличие карт
        String cardSenderNumber = transferMoneyDto.getCardSenderNumber();
        Card card = cardDao.getCardByCardNumber(cardSenderNumber);
        String cardRecipientNumber = transferMoneyDto.getCardRecipientNumber();
        ActionMoneyDto loadMoneyDto = new ActionMoneyDto(transferMoneyDto.getCardRecipientNumber(), transferMoneyDto.getAmount());
        ActionMoneyDto withdrawMoneyDto = new ActionMoneyDto(transferMoneyDto.getCardSenderNumber(), transferMoneyDto.getAmount());



        if (cardDao.getCardByCardNumber(cardSenderNumber) == null) {
            return "Card (" + cardSenderNumber + ") not found";
        } else if (card.getBalance() < transferMoneyDto.getAmount()) {
            return "Insufficient funds";
        } else if (cardDao.getCardByCardNumber(cardRecipientNumber) == null) {
            return "Card (" + cardRecipientNumber + ") not found";
        }

        String status = withdrawMoney(withdrawMoneyDto);
        if (status.equalsIgnoreCase("Successful")){
            status = loadMoney(loadMoneyDto);
            return status;
        } else return status;
    }



}
