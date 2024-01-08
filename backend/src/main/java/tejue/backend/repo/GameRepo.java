package tejue.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tejue.backend.model.Result;

@Repository
public interface GameRepo extends MongoRepository<Result, String> {
}
