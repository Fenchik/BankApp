package ru.maximen.converter.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maximen.converter.CardConverter;
import ru.maximen.dto.CardDto;
import ru.maximen.entity.Card;

@Slf4j
@Component
public class CardConverterImpl implements CardConverter {


    @Override
    public CardDto cardToDto(Card card) {
        if (card == null) {
            return null;
        } else {
            CardDto cardDto = new CardDto();
            cardDto.setCardNumber(card.getCardNumber());
            cardDto.setBalance(card.getBalance());
            cardDto.setCvc(card.getCvc());
            cardDto.setExpiredDate(card.getExpiredDate());
            cardDto.setPin(card.getPin());
            return cardDto;
        }
    }

    @Override
    public Card DtoToCard(CardDto cardDto) {
        if (cardDto == null) {
            log.warn("no cardDto");
            return null;
        } else  {
            Card card = new Card();
            card.setCardNumber(cardDto.getCardNumber());
            card.setBalance(cardDto.getBalance());
            card.setCvc(cardDto.getCvc());
            card.setExpiredDate(cardDto.getExpiredDate());
            card.setPin(cardDto.getPin());
            log.info(card.toString());
            return card;
        }
    }
}
