package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.model.DbResult;
import tejue.backend.model.Game;
import tejue.backend.model.Player;
import tejue.backend.repo.GameRepo;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo repo;

    public List<Game> getAllGames(String playerId) {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("No player found!"));

        return player.getGames();
    }

    public Player savePlayerResult(String playerId, String gameId, Map<String, DbResult> playerResult) {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("No player found!"));

        List<Game> games = player.getGames();

        Optional<Game> foundGame = games.stream()
                .filter(game -> game.getGameId().equals(gameId))
                .findFirst();

        if (foundGame.isPresent()) {
            List<DbResult> dbPlayerResult = new ArrayList<>(playerResult.values());
            foundGame.get().setPlayerResult(dbPlayerResult);
        }

        repo.save(player);
        return player;
    }
}

