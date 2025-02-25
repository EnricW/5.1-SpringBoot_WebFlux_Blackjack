package cat.itacademy.s05.S05.model;

import cat.itacademy.s05.S05.enums.Rank;
import cat.itacademy.s05.S05.enums.Suit;
import cat.itacademy.s05.S05.exception.custom.DeckIsEmptyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = initializeDeck();
        shuffle();
    }

    private List<Card> initializeDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank value : Rank.values()) {
                deck.add(new Card(value, suit));
            }
        }
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new DeckIsEmptyException("Deck is empty, cannot draw a card.");
        }
        return cards.remove(0);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
