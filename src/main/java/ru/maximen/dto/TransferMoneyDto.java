package ru.maximen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class TransferMoneyDto {

    @Size(min = 16, max = 16)
    private String cardSenderNumber;

    @Size(min = 16, max = 16)
    private String cardRecipientNumber;

    private Float amount;

}
