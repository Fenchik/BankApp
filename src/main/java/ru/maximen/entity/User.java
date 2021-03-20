package ru.maximen.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    private String name;

    private String surname;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER, mappedBy = "user")
    private List<Card> cards;
}
