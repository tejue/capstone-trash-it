package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.Player;
import tejue.backend.model.Result;
import tejue.backend.repo.GameRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo repo;
    public List<Player> getAllGameResults() {
        return repo.findAll();
    }
}
