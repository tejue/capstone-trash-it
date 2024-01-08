package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.Result;
import tejue.backend.repo.GameRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo repo;
    public List<Result> getAllGameResults() {
        return repo.findAll();
    }
}
