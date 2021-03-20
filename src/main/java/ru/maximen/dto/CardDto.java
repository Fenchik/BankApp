package ru.maximen.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CardDto {


    @Size(min=16, max=16)
    private String cardNumber;

    private Long pin;

    private Float balance;

    private String expiredDate;

    private Long cvc;

}
