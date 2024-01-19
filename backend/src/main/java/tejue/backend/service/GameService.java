package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.*;
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

    public Player savePlayerResult(String playerId, String gameId, Map<String, DbResult> playerResult) throws PlayerNotFoundException {
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

    public Player saveDataResult(String playerId, String gameId, List<Trash> gameData) throws PlayerNotFoundException {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found!"));

        List<Game> games = player.getGames();

        Optional<Game> foundGame = games.stream()
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

    public List<GamePoints> getGameResult(String playerId, String gameId) throws PlayerNotFoundException {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found!"));

        List<Game> games = player.getGames();
        List<GamePoints> gamePointsList = new ArrayList<>();

        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                List<DbResult> playerResult = game.getPlayerResult();
                List<DbResult> dataResult = game.getDataResult();

                List<SetOfPoints> setOfPoints = calculateSetOfPoints(playerResult, dataResult);
                int playerPointsTotal = setOfPoints.stream().mapToInt(SetOfPoints::getPlayerPoints).sum();
                int dataPointsTotal = setOfPoints.stream().mapToInt(SetOfPoints::getDataPoints).sum();

                GamePoints gamePoints = new GamePoints(playerPointsTotal, dataPointsTotal, setOfPoints);
                gamePointsList.add(gamePoints);
            }
        }
        return gamePointsList;
    }

    private List<SetOfPoints> calculateSetOfPoints(List<DbResult> playerResult, List<DbResult> dataResult) {
        List<SetOfPoints> setOfPointsList = new ArrayList<>();

        Map<String, Integer> dataPointsPerTrashCan = calculateDataPointsPerTrashCan(dataResult);

        int amountTrashCans = dataResult.size();


        for (int i = 0; i < amountTrashCans; i++) {
            DbResult playerResultPerTrashCan = getPlayerResultPerTrashCan(playerResult, i);
            DbResult dataResultPerTrashCan = dataResult.get(i);

            int playerPointsPerTrashCanCount = calculatePlayerPointsPerTrashCan(playerResultPerTrashCan, dataResultPerTrashCan);
            int dataPointsCount = dataPointsPerTrashCan.get(String.valueOf(i + 1));

            SetOfPoints setOfPoints = new SetOfPoints(String.valueOf(i + 1), playerPointsPerTrashCanCount, dataPointsCount);
            setOfPointsList.add(setOfPoints);


        }

        return setOfPointsList;
    }

    /*    private SetOfPoints caluclateDataPoints(List<DbResult> dataResult) {
            int dataPointsTotal = calculateDataPointsTotal(dataResult);
            Map<String, Integer> dataPointsPerTrashCan = calculateDataPointsPerTrashCan(dataResult);

            return new SetOfPoints(dataPointsTotal, dataPointsPerTrashCan);
        }*/

    private Map<String, Integer> calculateDataPointsPerTrashCan(List<DbResult> dataResult) {
        Map<String, Integer> trashIdsPerTrashCan = new HashMap<>();

        for (DbResult dbDataResult : dataResult) {
            String trashCanId = String.valueOf(dbDataResult.getTrashCanId());
            int trashIdsPerTrashCanCount = dbDataResult.getTrashIds().size();

            trashIdsPerTrashCan.put(trashCanId, trashIdsPerTrashCanCount);
        }
        return trashIdsPerTrashCan;
    }

   /* private SetOfPoints calculatePlayerPoints(List<DbResult> playerResult, List<DbResult> dataResult) {
        int playerPointsTotal = 0;
        Map<String, Integer> playerPointsPerTrashCan = new HashMap<>();

        int amountTrashCans = dataResult.size();

        for (int i = 0; i < amountTrashCans; i++) {
            DbResult playerResultPerTrashCan = getPlayerResultPerTrashCan(playerResult, i);
            DbResult dataResultPerTrashCan = dataResult.get(i);

            int playerPointsPerTrashCanCount = calculatePlayerPointsPerTrashCan(playerResultPerTrashCan, dataResultPerTrashCan);

            playerPointsTotal += playerPointsPerTrashCanCount;
            playerPointsPerTrashCan.put(String.valueOf(i + 1), playerPointsPerTrashCanCount);
        }
        return new SetOfPoints(playerPointsTotal, playerPointsPerTrashCan);
    }*/

    private DbResult getPlayerResultPerTrashCan(List<DbResult> playerResult, int trashCanIndex) {
        return trashCanIndex < playerResult.size() ? playerResult.get(trashCanIndex) : new DbResult();
        /*      if (trashCanIndex < playerResult.size()) {
            return playerResult.get(trashCanIndex);
        } else {
            return new DbResult();
        }*/
    }

    private int calculatePlayerPointsPerTrashCan(DbResult playerResult, DbResult dataResult) {
        List<String> playerTrashIds = playerResult.getTrashIds();
        List<String> dataTrashIds = dataResult.getTrashIds();

        return (int) playerTrashIds.stream().filter(dataTrashIds::contains).count();

      /*  int playerPointsPerTrashCan = 0;

        for (String playerTrashId : playerTrashIds) {
            if (dataTrashIds.contains(playerTrashId)) {
                playerPointsPerTrashCan++;
            }
        }
        return playerPointsPerTrashCan;*/
    }
/*    private int calculateDataPointsTotal(List<DbResult> dataResult) {
        //return dataResult.getTrashIds().size();

        int trashIdsTotal = 0;

        for (DbResult dbDataResult : dataResult) {
            trashIdsTotal += dbDataResult.getTrashIds().size();
        }
        return trashIdsTotal;
    }*/
}

