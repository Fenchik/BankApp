package ru.maximen.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximen.converter.CardConverter;
import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.CardDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.entity.Card;
import ru.maximen.entity.Transaction;
import ru.maximen.repository.CardRepository;
import ru.maximen.repository.TransactionRepository;
import ru.maximen.repository.UserRepository;
import ru.maximen.services.CardService;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {



    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private final CardConverter cardConverter;

    @Override
    @Transactional
    public String addCard(CardDto cardDto){

        Card card = cardConverter.DtoToCard(cardDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        card.setUser(userRepository.findByLogin(authentication.getName()));

        cardRepository.save(card);

        log.info("card added");
        return "card added";
    }

    @Override
    @Transactional
    public void deleteCard(String cardNumber){
        cardRepository.deleteCardByCardNumber(cardNumber);
    }

    @Override
    @Transactional
    public Float getBalance(String cardNumber){
        return cardRepository.getCardByCardNumber(cardNumber).getBalance();
    }

    @Override
    @Transactional
    public String loadMoney(ActionMoneyDto actionMoneyDto){

        String cardNumber = actionMoneyDto.getCardNumber();

        Card card = cardRepository.getCardByCardNumber(cardNumber);

        if (card == null) {
            return "Card ("+ actionMoneyDto.getCardNumber() + ") not found";
        }


        Float amount = actionMoneyDto.getAmount();
        Float balance = card.getBalance();

        balance += amount;

        cardRepository.updateCardBalanceByCardNumber(cardNumber, balance);

        Transaction transaction = new Transaction();
        transaction.setCardNumber(cardNumber);
        transaction.setDate(new Date());
        transaction.setAmount(amount);
        transaction.setBalance(balance);
        transaction.setType("load");
        transactionRepository.save(transaction);

        return "Successful";
    }

    @Override
    @Transactional
    public String withdrawMoney(ActionMoneyDto actionMoneyDto){
        Card card = cardRepository.getCardByCardNumber(actionMoneyDto.getCardNumber());
        if (card == null) {
            return "Card (" + actionMoneyDto.getCardNumber() + ") not found";
        }

        String cardNumber = actionMoneyDto.getCardNumber();
        Float amount = actionMoneyDto.getAmount();
        Float balance = card.getBalance();

        if (balance < amount) {
            return "Insufficient funds";
        } else {
            balance -= amount;
            cardRepository.updateCardBalanceByCardNumber(cardNumber, balance);

            Transaction transaction = new Transaction();
            transaction.setCardNumber(cardNumber);

            transaction.setDate(new Date());
            transaction.setAmount(amount);
            transaction.setBalance(cardRepository.getCardByCardNumber(cardNumber).getBalance());
            transaction.setType("withdraw");
            transactionRepository.save(transaction);

            return "Successful";
        }
    }

    @Override
    @Transactional
    public String transferMoney(TransferMoneyDto transferMoneyDto){
        //Проверка на возможность сняттия средств и наличие карт
        String cardSenderNumber = transferMoneyDto.getCardSenderNumber();
        Card card = cardRepository.getCardByCardNumber(cardSenderNumber);
        String cardRecipientNumber = transferMoneyDto.getCardRecipientNumber();
        ActionMoneyDto loadMoneyDto = new ActionMoneyDto(transferMoneyDto.getCardRecipientNumber(), transferMoneyDto.getAmount());
        ActionMoneyDto withdrawMoneyDto = new ActionMoneyDto(transferMoneyDto.getCardSenderNumber(), transferMoneyDto.getAmount());



        if (cardRepository.getCardByCardNumber(cardSenderNumber) == null) {
            return "Card (" + cardSenderNumber + ") not found";
        } else if (card.getBalance() < transferMoneyDto.getAmount()) {
            return "Insufficient funds";
        } else if (cardRepository.getCardByCardNumber(cardRecipientNumber) == null) {
            return "Card (" + cardRecipientNumber + ") not found";
        }

        String status = withdrawMoney(withdrawMoneyDto);
        if (status.equalsIgnoreCase("Successful")){
            status = loadMoney(loadMoneyDto);
            return status;
        } else return status;
    }



}
