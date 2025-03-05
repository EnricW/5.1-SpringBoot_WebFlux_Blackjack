package cat.itacademy.s05.S05.model;

import cat.itacademy.s05.S05.enums.Rank;
import cat.itacademy.s05.S05.enums.Suit;
import cat.itacademy.s05.S05.exception.custom.DeckIsEmptyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();
}
