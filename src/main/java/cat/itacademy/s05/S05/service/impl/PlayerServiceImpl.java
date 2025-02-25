package cat.itacademy.s05.S05.service.impl;

import cat.itacademy.s05.S05.exception.custom.PlayerNotFoundException;
import cat.itacademy.s05.S05.exception.custom.RankingIsEmptyException;
import cat.itacademy.s05.S05.model.Game;
import cat.itacademy.s05.S05.model.Player;
import cat.itacademy.s05.S05.repository.PlayerRepository;
import cat.itacademy.s05.S05.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Player> updatePlayerName(Long playerId, String newName) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id: " + playerId)))
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepository.save(player);
                })
                .doOnSuccess(updatedPlayer -> logger.info("Player name updated successfully for player ID: {}", updatedPlayer.getId()));
    }

    @Override
    public Flux<Player> getRanking() {
        logger.info("Fetching player rankings.");
        return playerRepository.findAll()
                .switchIfEmpty(Flux.error(new RankingIsEmptyException("Ranking is empty")));
    }

    @Override
    public Mono<Player> findByName(String name) {
        logger.info("Searching for player with name: {}", name);
        return playerRepository.findByName(name)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.info("Player not found, creating new player with name: {}", name);
                    Player newPlayer = new Player(null, name, 0);
                    return playerRepository.save(newPlayer);
                }));
    }

    @Override
    public Mono<Player> save(Player player) {
        return playerRepository.save(player)
                .doOnSuccess(savedPlayer -> logger.info("Player saved successfully with ID: {}", savedPlayer.getId()));
    }

    @Override
    public Mono<Void> updatePlayerStats(Game game) {
        return findByName(game.getPlayerName())
                .flatMap(player -> {
                    if ("player".equals(game.getWinner())) {
                        player.addWin();
                        logger.info("Player {} won the game. Updating stats.", player.getName());
                    }
                    return save(player);
                })
                .then();
    }
}
