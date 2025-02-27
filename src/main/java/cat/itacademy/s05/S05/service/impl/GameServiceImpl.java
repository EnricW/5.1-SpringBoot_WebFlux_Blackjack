package cat.itacademy.s05.S05.service.impl;

import cat.itacademy.s05.S05.enums.GameState;
import cat.itacademy.s05.S05.enums.PlayerMove;
import cat.itacademy.s05.S05.exception.custom.GameAlreadyEndedException;
import cat.itacademy.s05.S05.exception.custom.GameNotFoundException;
import cat.itacademy.s05.S05.exception.custom.GameNotInProgressException;
import cat.itacademy.s05.S05.exception.custom.InvalidMoveException;
import cat.itacademy.s05.S05.model.Game;
import cat.itacademy.s05.S05.repository.GameRepository;
import cat.itacademy.s05.S05.service.GameService;
import cat.itacademy.s05.S05.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImpl implements GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;
    private final PlayerService playerService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    @Override
    public Mono<Game> createGame(String playerName) {
        Game game = new Game();
        game.initializeGame(playerName);
        return gameRepository.save(game)
                .doOnSuccess(savedGame -> logger.info("Game created successfully with ID: {}", savedGame.getId()));
    }

    @Override
    public Flux<Game> getAllGames() {
        logger.info("Fetching all games from the database.");
        return gameRepository.findAll();
    }

    @Override
    public Mono<Game> getGame(String gameId) {
        logger.info("Fetching game with ID: {}", gameId);
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + gameId)));
    }

    @Override
    public Mono<Game> playMove(String gameId, String move) {
        return validateMove(move)
                .flatMap(playerMove -> gameRepository.findById(gameId)
                        .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + gameId)))
                        .flatMap(game -> validateGameState(game)
                                .flatMap(validatedGame -> processMove(validatedGame, playerMove))));
    }

    private Mono<PlayerMove> validateMove(String move) {
        return PlayerMove.fromString(move)
                .map(Mono::just)
                .orElseGet(() -> Mono.error(new InvalidMoveException("Invalid move: " + move)));
    }

    private Mono<Game> validateGameState(Game game) {
        if (game.getState() == GameState.ENDED) {
            return Mono.error(new GameAlreadyEndedException("Game already ended."));
        }
        if (game.getState() != GameState.IN_PROGRESS) {
            return Mono.error(new GameNotInProgressException("Cannot play move, game is not in progress."));
        }
        return Mono.just(game);
    }

    private Mono<Game> processMove(Game game, PlayerMove move) {
        game.executeMove(move);
        if (game.getState() == GameState.IN_PROGRESS) {
            return gameRepository.save(game);
        }

        return endGame(game);
    }

    private Mono<Game> endGame(Game game) {
        if (game.getState() != GameState.ENDED) {
            game.dealerTurn();
            game.determineWinner();
            game.setState(GameState.ENDED);
            logger.info("Game ended. Winner determined: {}", game.getWinner());
        }

        return playerService.updatePlayerStats(game)
                .then(gameRepository.save(game));
    }

    @Override
    public Mono<Void> deleteGame(String gameId) {
        logger.info("Deleting game with ID: {}", gameId);
        return gameRepository.deleteById(gameId);
    }
}
