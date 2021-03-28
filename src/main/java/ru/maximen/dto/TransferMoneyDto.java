package ru.maximen.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyDto {

    @Size(min = 16, max = 16)
    private String cardSenderNumber;

    @Size(min = 16, max = 16)
    private String cardRecipientNumber;

    private Float amount;

}
