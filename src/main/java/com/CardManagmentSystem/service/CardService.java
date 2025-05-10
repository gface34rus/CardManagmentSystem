package com.CardManagmentSystem.service;

import com.CardManagmentSystem.model.Card;
import com.CardManagmentSystem.model.CardStatus;
import com.CardManagmentSystem.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository; // Инициализация cardRepository
    }


    public Card createCard(Card card) {
        return cardRepository.save(card);
    }


    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }


    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
    }


    public Card updateCard(Long id, Card card) {
        Card existingCard = getCardById(id);
        existingCard.setOwner(card.getOwner());
        existingCard.setExpirationDate(card.getExpirationDate());
        existingCard.setStatus(card.getStatus());
        existingCard.setBalance(card.getBalance());
        return cardRepository.save(existingCard);
    }


    public boolean deleteCard(Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public boolean blockCard(Long id) {
        Card card = getCardById(id);
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        return true;
    }


    public boolean activateCard(Long id) {
        Card card = getCardById(id);
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
        return true;
    }

    public boolean transferFunds(Long fromId, Long toId, BigDecimal amount) {
        Card fromCard = getCardById(fromId);
        Card toCard = getCardById(toId);
        if (fromCard.getBalance().compareTo(amount) >= 0) {
            fromCard.setBalance(fromCard.getBalance().subtract(amount));
            toCard.setBalance(toCard.getBalance().add(amount));
            cardRepository.save(fromCard);
            cardRepository.save(toCard);
            return true;
        }
        return false;
    }
}