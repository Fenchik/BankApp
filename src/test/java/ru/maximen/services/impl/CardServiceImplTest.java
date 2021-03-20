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
import ru.maximen.converter.CardConverter;
import ru.maximen.dao.CardDao;
import ru.maximen.dao.TransactionDao;
import ru.maximen.dao.UserDao;
import ru.maximen.entity.Card;
import ru.maximen.entity.User;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardConverter cardConverter;

    @Mock
    private CardDao cardDao;

    @Mock
    private TransactionDao transactionDao;

    @Mock
    private UserDao userDao;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CardServiceImpl cardService;

    private static Card cardTrue, cardIncorrectNum, cardExist;

    @BeforeEach
    public void prepareTestData(){
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    public void addCard_added() {

        cardTrue = new Card(
                "1234567891011121",
                1234L,
                140.40f,
                "11.11.2023",
                new User(),
                253L
        );



        assertThat(cardService.addCard(cardConverter.cardToDto(cardTrue))).isEqualTo("Card added");
    }

    @Test
    public void addCard_incorrectnum() {


        cardIncorrectNum = new Card(
                "1234567891011121",
                1234L,
                140.40f,
                "11.11.2023",
                new User(),
                253L
        );
        assertThat(cardService.addCard(cardConverter.cardToDto(cardIncorrectNum))).isEqualTo("Incorrect card number");
    }

    @Test
    public void addCard_exist(){
        cardExist = new Card(
                "1234567891011121",
                1234L,
                140.40f,
                "11.11.2023",
                new User(),
                253L
        );


        when(cardDao.getCardByCardNumber(anyString())).thenReturn(cardExist);

        assertThat(cardService.addCard(cardConverter.cardToDto(cardExist))).isEqualTo("Such a card already exists");
    }


}