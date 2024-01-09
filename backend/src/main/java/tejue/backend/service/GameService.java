package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.Player;
import tejue.backend.model.Result;
import tejue.backend.repo.GameRepo;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo repo;

    public List<Result> getPlayerResults(String playerId) {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("No player found!"));

        List<Result> results = player.getResults();

        if (results != null && !results.isEmpty()) {
            return results;
        } else {
            throw new NoSuchElementException("No results found!");
        }
    }
}
