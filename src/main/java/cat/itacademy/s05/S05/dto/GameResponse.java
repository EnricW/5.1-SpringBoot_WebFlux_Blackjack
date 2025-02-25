package cat.itacademy.s05.S05.dto;

import cat.itacademy.s05.S05.enums.GameState;
import cat.itacademy.s05.S05.model.Game;
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
        this.playerCards = game.getPlayerHandCards() != null ? game.getPlayerHandCards().getCardNames() : null;
        this.playerScore = game.getPlayerHandCards() != null ? game.getPlayerHandCards().getScore() : 0;
        this.dealerCards = game.getDealerHandCards() != null ? game.getDealerHandCards().getCardNames() : null;
        this.dealerScore = game.getDealerHandCards() != null ? game.getDealerHandCards().getScore() : 0;
        this.winner = game.getWinner();
        this.remainingCards = game.getDeckRemainingCards() != null ? game.getDeckRemainingCards().size() : 0;
    }
}
