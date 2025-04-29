package com.CardManagmentSystem.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardNumber; // Зашифрованный номер карты

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private CardStatus status; // Активна, Заблокирована, Истек срок действия

    @Column(nullable = false)
    private BigDecimal balance;
}
