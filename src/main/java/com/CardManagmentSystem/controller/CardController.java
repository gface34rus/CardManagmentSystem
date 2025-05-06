package com.CardManagmentSystem.controller;

import com.CardManagmentSystem.model.Card;
import com.CardManagmentSystem.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        Card card = cardService.getCardById(id);
        if (card != null) {
            // Возвращаем карту с маскированным номером
            card.setCardNumber(card.getMaskedCardNumber());
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@Valid @RequestBody Card card) {
        Card createdCard = cardService.createCard(card);
        return ResponseEntity.status(201).body(createdCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Long id, @Valid @RequestBody Card card) {
        Card updatedCard = cardService.updateCard(id, card);
        if (updatedCard != null) {
            updatedCard.setCardNumber(updatedCard.getMaskedCardNumber());
            return ResponseEntity.ok(updatedCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        if (cardService.deleteCard(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockCard(@PathVariable Long id) {
        if (cardService.blockCard(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable Long id) {
        if (cardService.activateCard(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{fromId}/transfer/{toId}")
    public ResponseEntity<Void> transferFunds(@PathVariable Long fromId, @PathVariable Long toId, @RequestParam BigDecimal amount) {
        if (cardService.transferFunds(fromId, toId, amount)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
