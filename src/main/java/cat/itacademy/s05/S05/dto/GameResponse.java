package cat.itacademy.s05.S05.dto;

import cat.itacademy.s05.S05.model.Game;
import cat.itacademy.s05.S05.enums.GameState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse {
    private String id;
    private String playerName;
    private GameState state;
    private List<String> playerCards;
    private int playerScore;
    private List<String> dealerCards;
    private int dealerScore;
    private String winner;
    private int remainingCards;

    public GameResponse(Game game) {
        this.id = game.getId();
        this.playerName = game.getPlayerName();
        this.state = game.getState();
        this.playerCards = game.getPlayerHandCards().getCardNames();
        this.playerScore = game.getPlayerHandCards().getScore();
        this.dealerCards = game.getDealerHandCards().getCardNames();
        this.dealerScore = game.getDealerHandCards().getScore();
        this.winner = game.getWinner();
        this.remainingCards = game.getDeckRemainingCards() != null ? game.getDeckRemainingCards().size() : 0;
    }
}
