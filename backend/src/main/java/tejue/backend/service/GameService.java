package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.Player;
import tejue.backend.model.Round;
import tejue.backend.repo.GameRepo;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo repo;

    public List<Round> getAllRounds(String playerId) {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("No player found!"));

        return player.getRounds();
    }}

