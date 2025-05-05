package com.CardManagmentSystem.service;

import com.CardManagmentSystem.model.Card;
import com.CardManagmentSystem.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card) {
        // Логика для создания карты
        return cardRepository.save(card);
    }

    public List<Card> getAllCards() {
        // Логика для получения всех карт
        return cardRepository.findAll();
    }

    public Card getCardById(Long id) {
        // Логика для получения карты по ID
        return cardRepository.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public Card updateCard(Long id, Card card) {
        // Логика для обновления карты
        Card existingCard = getCardById(id);
        existingCard.setOwner(card.getOwner());
        existingCard.setExpirationDate(card.getExpirationDate());
        existingCard.setStatus(card.getStatus());
        existingCard.setBalance(card.getBalance());
        return cardRepository.save(existingCard);
    }

    public void deleteCard(Long id) {
        // Логика для удаления карты
        cardRepository.deleteById(id);
    }

    public void transferFunds(Long fromCardId, Long toCardId, BigDecimal amount) {
        // Логика для перевода средств между картами
        Card fromCard = getCardById(fromCardId);
        Card toCard = getCardById(toCardId);
        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));
        cardRepository.save(fromCard);
        cardRepository.save(toCard);
    }
}
