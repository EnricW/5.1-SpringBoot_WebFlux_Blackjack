package cat.itacademy.s05.S05.controller;

import cat.itacademy.s05.S05.dto.CreateGameRequest;
import cat.itacademy.s05.S05.dto.GameResponse;
import cat.itacademy.s05.S05.dto.PlayerMoveRequest;
import cat.itacademy.s05.S05.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Game API", description = "Endpoints for managing games")
@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Create a new game", description = "Creates a game for a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public Mono<ResponseEntity<GameResponse>> createGame(@Valid @RequestBody CreateGameRequest request) {
        return gameService.createGame(request.getPlayerName())
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(new GameResponse(game)));
    }

    @Operation(summary = "Get all games", description = "Retrieves all existing games")
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

    @Operation(summary = "Get a game by ID", description = "Retrieves a specific game by its ID")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GameResponse>> getGame(@PathVariable String id) {
        return gameService.getGame(id)
                .map(game -> ResponseEntity.ok().body(new GameResponse(game)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Play a move in a game", description = "Processes a player's move in a game")
    @PostMapping("/{id}/moves")
    public Mono<ResponseEntity<GameResponse>> playMove(@PathVariable String id, @Valid @RequestBody PlayerMoveRequest request) {
        return gameService.playMove(id, request.getMove())
                .map(game -> ResponseEntity.ok().body(new GameResponse(game)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a game", description = "Deletes a game by ID")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}