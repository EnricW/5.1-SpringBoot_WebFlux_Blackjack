package cat.itacademy.s05.S05.controller;

import cat.itacademy.s05.S05.dto.CreateGameRequest;
import cat.itacademy.s05.S05.dto.GameResponse;
import cat.itacademy.s05.S05.dto.PlayerMoveRequest;
import cat.itacademy.s05.S05.service.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Mono<ResponseEntity<GameResponse>> createGame(@Valid @RequestBody CreateGameRequest request) {
        return gameService.createGame(request.getPlayerName())
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(new GameResponse(game)));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<GameResponse>>> getAllGames() {
        return gameService.getAllGames()
                .map(GameResponse::new)
                .collectList()
                .map(games -> games.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok().body(Flux.fromIterable(games))
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<GameResponse>> getGame(@PathVariable String id) {
        return gameService.getGame(id)
                .map(game -> ResponseEntity.ok().body(new GameResponse(game)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/moves")
    public Mono<ResponseEntity<GameResponse>> playMove(@PathVariable String id, @Valid @RequestBody PlayerMoveRequest request) {
        return gameService.playMove(id, request.getMove())
                .map(game -> ResponseEntity.ok().body(new GameResponse(game)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}