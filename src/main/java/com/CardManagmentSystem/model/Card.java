package com.CardManagmentSystem.model;

import jakarta.persistence.*;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.Key;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Objects;

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
    private CardStatus status;

    @Column(nullable = false)
    private BigDecimal balance;

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "1234567890123456"; // Должен быть 16 символов для AES-128

    public Card() {
    }

    public Card(String number, String owner, LocalDate localDate, BigDecimal bigDecimal) {
        this.cardNumber = number;
        this.owner = owner;
        this.expirationDate = localDate;
        this.balance = bigDecimal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return decrypt(cardNumber);
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = encrypt(cardNumber);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "**** **** **** ****"; // Возвращаем маску, если номер карты некорректен
        }
        return "**** **** **** " + decrypt(cardNumber).substring(decrypt(cardNumber).length() - 4);
    }

    public static String encrypt(String data) {
        try {
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            String encodedData = Base64.getEncoder().encodeToString(encryptedData);
            System.out.println("Зашифрованные данные: " + encodedData); // Отладочное сообщение
            return encodedData;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка шифрования", e);
        }
    }

    private String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            throw new IllegalArgumentException("Данные для дешифрования не могут быть null или пустыми");
        }

        try {
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] originalData = cipher.doFinal(decodedData);
            return new String(originalData);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("Ошибка дешифрования: длина входных данных должна быть кратна 16", e);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка дешифрования", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return Objects.equals(id, card.id)
                && Objects.equals(cardNumber, card.cardNumber)
                && Objects.equals(owner, card.owner)
                && Objects.equals(expirationDate, card.expirationDate)
                && status == card.status
                && Objects.equals(balance, card.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, owner, expirationDate, status, balance);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", owner='" + owner + '\'' +
                ", expirationDate=" + expirationDate +
                ", status=" + status +
                ", balance=" + balance +
                '}';
    }
}
