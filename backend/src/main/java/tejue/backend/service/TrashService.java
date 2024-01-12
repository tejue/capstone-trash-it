package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.Trash;
import tejue.backend.repo.TrashRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrashService {

    private final TrashRepo repo;

    public List<Trash> getAllTrash() {
        return repo.findAll();
    }
}
