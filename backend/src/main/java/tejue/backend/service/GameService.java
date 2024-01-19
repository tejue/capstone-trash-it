package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.DbResult;
import tejue.backend.model.Game;
import tejue.backend.model.Player;
import tejue.backend.model.Trash;
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

    public Player savePlayerResult(String playerId, String gameId, Map<String, DbResult> playerResult) throws PlayerNotFoundException{
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found!"));

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

    public tejue.backend.model.Player saveDataResult(String playerId, String gameId, List<Trash> gameData) throws PlayerNotFoundException {
        tejue.backend.model.Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found!"));

        List<tejue.backend.model.Game> games = player.getGames();

        Optional<tejue.backend.model.Game> foundGame = games.stream()
                .filter(game -> game.getGameId().equals(gameId))
                .findFirst();

        if (foundGame.isPresent()) {
            List<DbResult> dbDataResult = transformGameData(gameData);
            foundGame.get().setDataResult(dbDataResult);
        }

        repo.save(player);
        return player;
    }

    public List<DbResult> transformGameData(List<Trash> gameData) {
        Map<String, List<String>> gameDataMap = new HashMap<>();

        gameData.forEach(data -> {
            String trashCanId = data.getTrashCanId();
            String trashId = data.getId();

            if (gameDataMap.containsKey(trashCanId)) {
                gameDataMap.get(trashCanId).add(trashId);
            } else {
                List<String> trashIds = new ArrayList<>();
                trashIds.add(trashId);
                gameDataMap.put(trashCanId, trashIds);
            }
        });

        return gameDataMap.entrySet().stream()
                .map(entry -> new DbResult(entry.getKey(), entry.getValue()))
                .toList();
    }
}

