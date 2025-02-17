package cat.itacademy.s05.S05.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class Game {
    private String id;
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private boolean gameOver;

    public Game(Player player) {
        this.player = player;
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.gameOver = false;
    }

    public void startGame() {
        player.drawCard(deck);
        player.drawCard(deck);
        dealer.drawCard(deck);
        dealer.drawCard(deck);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }
}
