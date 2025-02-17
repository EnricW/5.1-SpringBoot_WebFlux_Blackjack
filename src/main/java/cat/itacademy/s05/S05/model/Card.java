package cat.itacademy.s05.S05.model;

import cat.itacademy.s05.S05.enums.Rank;
import cat.itacademy.s05.S05.enums.Suit;

public class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
