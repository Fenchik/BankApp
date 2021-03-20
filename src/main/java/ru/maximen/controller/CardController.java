package ru.maximen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.maximen.dto.CardDto;
import ru.maximen.entity.Card;
import ru.maximen.entity.Transaction;
import ru.maximen.services.CardService;
import ru.maximen.services.TransactionService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final TransactionService transactionService;

    @PostMapping(value = "/add")
    public String addNewCard(@Valid @RequestBody CardDto cardDto){
        return cardService.addCard(cardDto);
    }

    @DeleteMapping(value = "/delete/{cardNumber}")
    public String deleteCard(@PathVariable(value = "cardNumber") String cardNumber){
        cardService.deleteCard(cardNumber);
        return "Delete";
    }

    @GetMapping("/gettranshist/{cardNumber}/{startDate}/{endDate}")
    public List<Transaction> getTransactionHistory(
            @PathVariable("cardNumber") String cardNumber,
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm") Date startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm") Date endDate
            ){
        return transactionService.GetTransactionHistory(cardNumber,startDate,endDate);
    }

}
