package cat.itacademy.s05.S05.controller;

import cat.itacademy.s05.S05.dto.PlayerResponse;
import cat.itacademy.s05.S05.dto.UpdatePlayerNameRequest;
import cat.itacademy.s05.S05.exception.custom.RankingIsEmptyException;
import cat.itacademy.s05.S05.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<Void>> updatePlayerName(@PathVariable Long playerId, @Valid @RequestBody UpdatePlayerNameRequest request) {
        return playerService.updatePlayerName(playerId, request.getPlayerName())
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/ranking")
    public Mono<ResponseEntity<Flux<PlayerResponse>>> getRanking() {
        return playerService.getRanking()
                .map(PlayerResponse::new)
                .collectList()
                .map(players -> players.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok().body(Flux.fromIterable(players))
                );
    }
}