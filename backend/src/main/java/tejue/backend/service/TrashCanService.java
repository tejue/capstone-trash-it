package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.TrashCan;
import tejue.backend.repo.TrashCanRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrashCanService {

    private final TrashCanRepo repo;

    public List<TrashCan> getAllTrashCans() {
           return repo.findAll();
    }
}
