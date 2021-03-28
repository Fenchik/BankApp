package ru.maximen.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionMoneyDto {

    @Size(min = 16, max = 16)
    private String cardNumber;

    private Float amount;
}
