package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.Trash;
import tejue.backend.repo.TrashRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrashService {

    private final TrashRepo repo;

    public List<Trash> getAllTrash() {
        List<Trash> allTrash = repo.findAll();

        Collections.shuffle(allTrash);
        int size = Math.min(10, allTrash.size());

        return new ArrayList<>(allTrash.subList(0, size));
    }
}
