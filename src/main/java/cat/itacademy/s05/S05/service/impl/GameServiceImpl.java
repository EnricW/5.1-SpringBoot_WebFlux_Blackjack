package cat.itacademy.s05.S05.service.impl;

import cat.itacademy.s05.S05.enums.GameState;
import cat.itacademy.s05.S05.enums.PlayerMove;
import cat.itacademy.s05.S05.exception.custom.GameAlreadyEndedException;
import cat.itacademy.s05.S05.exception.custom.GameNotFoundException;
import cat.itacademy.s05.S05.exception.custom.GameNotInProgressException;
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
    public Mono<Game> playMove(String gameId, PlayerMove move) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + gameId)))
                .flatMap(game -> {
                    if (game.getState() == GameState.ENDED) {
                        return Mono.error(new GameAlreadyEndedException("Game already ended."));
                    }
                    if (game.getState() != GameState.IN_PROGRESS) {
                        return Mono.error(new GameNotInProgressException("Cannot play move, game is not in progress."));
                    }
                    game.executeMove(move);
                    return endGame(game);
                })
                .doOnSuccess(updatedGame -> logger.info("Move executed successfully for game ID: {}", updatedGame.getId()));
    }

    private Mono<Game> endGame(Game game) {
        game.determineWinner();
        logger.info("Game ended. Winner determined for game ID: {}", game.getId());
        return playerService.updatePlayerStats(game)
                .then(gameRepository.save(game));
    }

    @Override
    public Mono<Void> deleteGame(String gameId) {
        logger.info("Deleting game with ID: {}", gameId);
        return gameRepository.deleteById(gameId);
    }
}
