package ru.maximen.converter;

import ru.maximen.dto.CardDto;
import ru.maximen.entity.Card;

public interface CardConverter {

    public CardDto cardToDto(Card card);

    public Card DtoToCard(CardDto cardDto);

}
