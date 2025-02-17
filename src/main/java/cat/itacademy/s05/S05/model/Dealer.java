package cat.itacademy.s05.S05.model;

public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public void drawCard(Deck deck) {
        hand.addCard(deck.drawCard());
    }

    public int getHandValue() {
        return hand.calculateValue();
    }

    public boolean shouldDraw() {
        return getHandValue() < 17;
    }

    public Hand getHand() {
        return hand;
    }
}
