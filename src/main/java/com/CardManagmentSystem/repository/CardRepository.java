package com.CardManagmentSystem.repository;

import com.CardManagmentSystem.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {

}
