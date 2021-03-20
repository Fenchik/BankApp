package ru.maximen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.services.CardService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/money")
public class CashController {

    private final CardService cardService;

    @GetMapping("/get/{cardNumber}")
    public String checkBalance(@PathVariable(value = "cardNumber") String cardNumber){

        return cardService.getBalance(cardNumber).toString();
    }

    @PostMapping("/add")
    public String loadMoney(@Valid @RequestBody ActionMoneyDto actionMoneyDto){
        return cardService.loadMoney(actionMoneyDto);
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@Valid @RequestBody ActionMoneyDto actionMoneyDto){
        return cardService.withdrawMoney(actionMoneyDto);
    }


    @PostMapping("/transfer")
    public String transferMoney(@Valid @RequestBody TransferMoneyDto transferMoneyDto){
        return cardService.transferMoney(transferMoneyDto);
    }

}
