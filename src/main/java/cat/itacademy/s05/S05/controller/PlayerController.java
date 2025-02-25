package cat.itacademy.s05.S05.controller;

import cat.itacademy.s05.S05.dto.PlayerResponse;
import cat.itacademy.s05.S05.dto.UpdatePlayerNameRequest;
import cat.itacademy.s05.S05.exception.custom.RankingIsEmptyException;
import cat.itacademy.s05.S05.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PutMapping("/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updatePlayerName(@PathVariable Long playerId, @Valid @RequestBody UpdatePlayerNameRequest request) {
        return playerService.updatePlayerName(playerId, request.getPlayerName()).then();
    }

    @GetMapping("/ranking")
    public Flux<PlayerResponse> getRanking() {
        return playerService.getRanking()
                .sort((p1, p2) -> Integer.compare(p2.getWins(), p1.getWins()))
                .take(10)
                .map(PlayerResponse::new)
                .switchIfEmpty(Flux.error(new RankingIsEmptyException("No players found in ranking.")));
    }
}
