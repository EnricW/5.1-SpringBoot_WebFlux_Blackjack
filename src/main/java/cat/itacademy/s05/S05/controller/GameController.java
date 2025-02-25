package cat.itacademy.s05.S05.controller;

import cat.itacademy.s05.S05.dto.CreateGameRequest;
import cat.itacademy.s05.S05.dto.GameResponse;
import cat.itacademy.s05.S05.dto.PlayerMoveRequest;
import cat.itacademy.s05.S05.service.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponse> createGame(@Valid @RequestBody CreateGameRequest request) {
        return gameService.createGame(request.getPlayerName())
                .map(GameResponse::new);
    }

    @GetMapping
    public Flux<GameResponse> getAllGames() {
        return gameService.getAllGames()
                .map(GameResponse::new);
    }

    @GetMapping("/{id}")
    public Mono<GameResponse> getGame(@PathVariable String id) {
        return gameService.getGame(id)
                .map(GameResponse::new);
    }

    @PostMapping("/{id}/moves")
    public Mono<GameResponse> playMove(@PathVariable String id, @Valid @RequestBody PlayerMoveRequest request) {
        return gameService.playMove(id, request.getMove())
                .map(GameResponse::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }
}
