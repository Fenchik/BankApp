package ru.maximen.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=16, max=16)
    private String cardNumber;

    private Long pin;

    private Float balance;

    private String expiredDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long cvc;

    public Card(String cardNumber, Long pin, Float balance, String expiredDate, User user, Long cvc) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.expiredDate = expiredDate;
        this.user = user;
        this.cvc = cvc;
    }
}
