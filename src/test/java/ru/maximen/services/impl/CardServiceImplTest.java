package ru.maximen.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.maximen.converter.CardConverter;
import ru.maximen.dto.ActionMoneyDto;
import ru.maximen.dto.CardDto;
import ru.maximen.dto.TransferMoneyDto;
import ru.maximen.entity.Card;
import ru.maximen.entity.User;
import ru.maximen.repository.CardRepository;
import ru.maximen.repository.TransactionRepository;
import ru.maximen.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardConverter cardConverter;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CardServiceImpl cardService;

    private static Card cardTrue;



    @Test
    void addCard_added() {

        cardTrue = new Card(
                "1234567891011121",
                1234L,
                140.40f,
                "11.11.2023",
                new User(),
                253L
        );
        CardDto cardDto = cardConverter.cardToDto(cardTrue);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(cardConverter.DtoToCard(cardDto)).thenReturn(cardTrue);

        assertThat(cardService.addCard(cardConverter.cardToDto(cardTrue))).isEqualTo("card added");
    }



    @Test
    void getBalance() {
        String cardNumber = "123412341234";
        Float balance = 100f;

        Card card = new Card(cardNumber,123l, balance, "someDate", null, 333l);

        when(cardRepository.getCardByCardNumber(cardNumber)).thenReturn(card);

        assertThat(cardService.getBalance(cardNumber)).isEqualTo(balance);
    }


    @Test
    void loadMoneyCardNotFound() {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("123412342134", 150f);


        when(cardRepository.getCardByCardNumber(anyString())).thenReturn(null);

        assertThat(cardService.loadMoney(actionMoneyDto)).isEqualTo("Card ("+ actionMoneyDto.getCardNumber() + ") not found");
    }

    @Test
    void loadMoneySuccess() {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("123412342134", 150f);

        Card card = new Card("123412341234",123l, 100f, "someDate", null, 333l);

        when(cardRepository.getCardByCardNumber(anyString())).thenReturn(card);

        assertThat(cardService.loadMoney(actionMoneyDto)).isEqualTo("Successful");
    }

    @Test
    void withdrawMoneyCardNotFound() {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("123412342134", 150f);

        when(cardRepository.getCardByCardNumber(anyString())).thenReturn(null);

        assertThat(cardService.withdrawMoney(actionMoneyDto)).isEqualTo("Card ("+ actionMoneyDto.getCardNumber() + ") not found");
    }

    @Test
    void withdrawMoneyInsufficientFunds() {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("123412342134", 150f);

        Card card = new Card("123412341234",123l, 100f, "someDate", null, 333l);

        when(cardRepository.getCardByCardNumber(anyString())).thenReturn(card);

        assertThat(cardService.withdrawMoney(actionMoneyDto)).isEqualTo("Insufficient funds");
    }

    @Test
    void withdrawMoneySuccess() {
        ActionMoneyDto actionMoneyDto = new ActionMoneyDto("123412342134", 150f);

        Card card = new Card("123412341234",123l, 200f, "someDate", null, 333l);

        when(cardRepository.getCardByCardNumber(anyString())).thenReturn(card);

        assertThat(cardService.withdrawMoney(actionMoneyDto)).isEqualTo("Successful");
    }


    @Test
    void transferMoneyNotFoundSenderCard() {
        TransferMoneyDto transferMoneyDto = new TransferMoneyDto("123412342134","123412342135", 150f);

        Card card = null;

        when(cardRepository.getCardByCardNumber(transferMoneyDto.getCardSenderNumber())).thenReturn(card);

        assertThat(cardService.transferMoney(transferMoneyDto)).isEqualTo("Card (" + transferMoneyDto.getCardSenderNumber() + ") not found");

    }

    @Test
    void transferMoneyNotFoundRecipientCard() {
        TransferMoneyDto transferMoneyDto = new TransferMoneyDto("123412342134","123412342135", 150f);

        Card card = new Card(transferMoneyDto.getCardSenderNumber(),123l, 200f, "someDate", null, 333l);

        when(cardRepository.getCardByCardNumber(transferMoneyDto.getCardSenderNumber())).thenReturn(card);

        when(cardRepository.getCardByCardNumber(transferMoneyDto.getCardRecipientNumber())).thenReturn(null);

        assertThat(cardService.transferMoney(transferMoneyDto)).isEqualTo("Card (" + transferMoneyDto.getCardRecipientNumber() + ") not found");
    }

    @Test
    void transferMoneyInsufficientFunds(){

        TransferMoneyDto transferMoneyDto = new TransferMoneyDto("123412342134","123412342135", 150f);

        Card card = new Card(transferMoneyDto.getCardSenderNumber(),123l, 100f, "someDate", null, 333l);

        when(cardRepository.getCardByCardNumber(anyString())).thenReturn(card);

        assertThat(cardService.transferMoney(transferMoneyDto)).isEqualTo( "Insufficient funds");
    }

    @Test
    void transferMoneySuccess() {
        TransferMoneyDto transferMoneyDto = new TransferMoneyDto("123412342134","123412342135", 150f);

        Card card1 = new Card(transferMoneyDto.getCardSenderNumber(),123l, 200f, "someDate", null, 333l);

        Card card2 = new Card(transferMoneyDto.getCardRecipientNumber(),123l, 200f, "someDate", null, 333l);



        when(cardRepository.getCardByCardNumber(transferMoneyDto.getCardSenderNumber())).thenReturn(card1);

        when(cardRepository.getCardByCardNumber(transferMoneyDto.getCardRecipientNumber())).thenReturn(card2);

        assertThat(cardService.transferMoney(transferMoneyDto)).isEqualTo("Successful");
    }


}