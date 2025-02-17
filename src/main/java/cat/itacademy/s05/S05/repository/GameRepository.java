package cat.itacademy.s05.S05.repository;

import cat.itacademy.s05.S05.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Mono<Game> findById(String id);
}
