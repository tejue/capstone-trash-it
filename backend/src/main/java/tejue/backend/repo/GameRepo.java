package tejue.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tejue.backend.model.Player;

@Repository
public interface GameRepo extends MongoRepository<Player, String> {
}
