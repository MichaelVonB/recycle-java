package com.dddeurope.recycle.repository;

import com.dddeurope.recycle.domain.model.CardId;
import com.dddeurope.recycle.domain.model.Visit;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VisitsRepository extends com.dddeurope.recycle.repository.Repository {

    private Map<CardId, Visit> visits = new HashMap<>();

    public void enter(CardId cardId, LocalDate date) {
        visits.put(cardId, new Visit(cardId, date));
    }

    public Visit leave(CardId cardId) {
        return visits.remove(cardId);
    }

    public Visit getByCardId(CardId cardId) {
        return visits.get(cardId);
    }

    @Override
    public void reset() {
        this.visits = new HashMap<>();
    }
}
