package cat.itacademy.s05.S05.model;

import cat.itacademy.s05.S05.enums.GameState;
import cat.itacademy.s05.S05.enums.PlayerMove;
import cat.itacademy.s05.S05.exception.custom.DeckIsEmptyException;
import cat.itacademy.s05.S05.exception.custom.GameAlreadyEndedException;
import cat.itacademy.s05.S05.exception.custom.InvalidMoveException;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
@JsonPropertyOrder({"id", "playerName", "state", "playerHandCards", "dealerHandCards", "winner", "deckRemainingCards"})
public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    @Id
    private String id;
    private String playerName;
    private GameState state;
    private Hand playerHandCards;
    private Hand dealerHandCards;
    private String winner;
    private List<Card> deckRemainingCards;

    public void initializeGame(String playerName) {
        this.playerName = playerName;
        this.state = GameState.IN_PROGRESS;
        Deck deck = new Deck();
        this.playerHandCards = new Hand();
        this.dealerHandCards = new Hand();
        dealInitialCards(deck);
        this.deckRemainingCards = deck.getCards();
        logger.info("Game initialized successfully for player: {}", playerName);
    }

    private void dealInitialCards(Deck deck) {
        playerHandCards.addCard(deck.drawCard());
        playerHandCards.addCard(deck.drawCard());
        dealerHandCards.addCard(deck.drawCard());
        dealerHandCards.addCard(deck.drawCard());
    }

    public void executeMove(PlayerMove move) {
        if (this.state == GameState.ENDED) {
            throw new GameAlreadyEndedException("Game already ended.");
        }
        if (move == null) {
            throw new InvalidMoveException("Move cannot be null.");
        }
        switch (move) {
            case HIT -> handleHitMove();
            case STAND -> handleStandMove();
            default -> throw new InvalidMoveException("Invalid move: " + move);
        }
        logger.info("Move executed: {} for player: {}", move, playerName);
    }

    private void handleHitMove() {
        if (deckRemainingCards == null || deckRemainingCards.isEmpty()) {
            throw new DeckIsEmptyException("Deck is empty.");
        }

        playerHandCards.addCard(deckRemainingCards.remove(0));
        logger.info("Player {} hit. New hand total: {}", playerName, playerHandCards.calculateTotal());

        if (playerHandCards.calculateTotal() >= 21) {
            state = GameState.ENDED;
            logger.info("Game ended after hit move. Player total: {}", playerHandCards.calculateTotal());
        }
    }

    private void handleStandMove() {
        dealerTurn();
        state = GameState.ENDED;
        logger.info("Game ended after stand move.");
    }

    private void dealerTurn() {
        while (this.dealerHandCards.calculateTotal() < 17 && !deckRemainingCards.isEmpty()) {
            this.dealerHandCards.addCard(deckRemainingCards.remove(0));
        }
        logger.info("Dealer's turn ended with total: {}", dealerHandCards.calculateTotal());
    }

    public void determineWinner() {
        int playerScore = playerHandCards.calculateTotal();
        int dealerScore = dealerHandCards.calculateTotal();

        if (playerHandCards.isBust() || dealerScore > playerScore) {
            winner = "dealer";
        } else if (dealerHandCards.isBust() || playerScore > dealerScore) {
            winner = "player";
        } else {
            winner = "tie";
        }
        logger.info("Game result determined. Winner: {}", winner);
    }
}
