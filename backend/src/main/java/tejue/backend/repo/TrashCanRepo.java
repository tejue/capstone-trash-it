package tejue.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tejue.backend.model.TrashCan;

@Repository
public interface TrashCanRepo extends MongoRepository<TrashCan, String> {
}
