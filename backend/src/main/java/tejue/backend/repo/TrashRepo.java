package tejue.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tejue.backend.model.Trash;

@Repository
public interface TrashRepo extends MongoRepository<Trash, String> {
}
