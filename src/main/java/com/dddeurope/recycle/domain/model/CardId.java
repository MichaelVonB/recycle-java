package com.dddeurope.recycle.domain.model;

public record CardId(String cardI) {
    public static CardId of(String cardId) {
        return new CardId(cardId);
    }

}
