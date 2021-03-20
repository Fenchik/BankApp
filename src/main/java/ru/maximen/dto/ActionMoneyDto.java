package ru.maximen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class ActionMoneyDto {

    @Size(min = 16, max = 16)
    private String cardNumber;

    private Float amount;
}
