package tejue.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tejue.backend.exception.GameNotFoundException;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.*;
import tejue.backend.repo.GameRepo;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo repo;
    private final IdService idService;

    public String playerNotFoundMessage(String playerId) {
        return "Player with id " + playerId + " not found";
    }

    public String gameNotFoundMessage(String gameId) {
        return "Game with id " + gameId + " not found";
    }

    public String allGamesNotFoundMessage(String playerId) {
        return "No games found for player with id " + playerId;
    }

    public Player createNewPlayer(PlayerDTO newPlayerDTO) {
        Player newPlayer = new Player(idService.randomId(), newPlayerDTO.getName(), newPlayerDTO.getGames());

        repo.save(newPlayer);
        return newPlayer;
    }

    public Player getPlayerById(String playerId) throws PlayerNotFoundException {
        return repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }

    public Player deletePlayerById(String playerId) throws PlayerNotFoundException {
        Player playerToDelete = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        repo.deleteById(playerId);
        return playerToDelete;
    }

    public Player saveDataResult(String playerId, List<Trash> gameData) throws PlayerNotFoundException {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerNotFoundMessage(playerId)));

        if (player.getGames() == null) {
            player.setGames(new ArrayList<>());
        }

        List<Game> games = player.getGames();

        Game newGame = new Game();
        newGame.setGameId(String.valueOf(games.size() + 1));
        newGame.setDataResult(transformGameData(gameData));
        games.add(newGame);

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

    public Player savePlayerResult(String playerId, String gameId, Map<String, DbResult> playerResult) throws PlayerNotFoundException, GameNotFoundException {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerNotFoundMessage(playerId)));

        List<Game> games = player.getGames();

        Game foundGame = games.stream()
                .filter(game -> game.getGameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new GameNotFoundException(gameNotFoundMessage(gameId)));

        List<DbResult> dbPlayerResult = new ArrayList<>(playerResult.values());
        foundGame.setPlayerResult(dbPlayerResult);

        repo.save(player);
        return player;
    }

    public List<GamePoints> getAllGamesResult(String playerId) throws PlayerNotFoundException {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerNotFoundMessage(playerId)));

        List<Game> games = player.getGames();

        List<GamePoints> allGamesPoints = new ArrayList<>();

        for (Game game : games) {
            List<DbResult> playerResult = game.getPlayerResult();
            List<DbResult> dataResult = game.getDataResult();

            List<SetOfPoints> setOfPoints = calculateSetOfPoints(playerResult, dataResult);
            int playerPointsTotal = setOfPoints.stream().mapToInt(SetOfPoints::getPlayerPoints).sum();
            int dataPointsTotal = setOfPoints.stream().mapToInt(SetOfPoints::getDataPoints).sum();

            GamePoints gamePoints = new GamePoints(playerPointsTotal, dataPointsTotal, setOfPoints);
            allGamesPoints.add(gamePoints);
        }

        return allGamesPoints;
    }

    public GamePoints getGameResult(String playerId, String gameId) throws PlayerNotFoundException, GameNotFoundException {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerNotFoundMessage(playerId)));

        List<Game> games = player.getGames();
        if (games.isEmpty()) {
            throw new GameNotFoundException(allGamesNotFoundMessage(playerId));
        }

        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                List<DbResult> playerResult = game.getPlayerResult();
                List<DbResult> dataResult = game.getDataResult();

                List<SetOfPoints> setOfPoints = calculateSetOfPoints(playerResult, dataResult);
                int playerPointsTotal = setOfPoints.stream().mapToInt(SetOfPoints::getPlayerPoints).sum();
                int dataPointsTotal = setOfPoints.stream().mapToInt(SetOfPoints::getDataPoints).sum();

                return new GamePoints(playerPointsTotal, dataPointsTotal, setOfPoints);
            }
        }
        throw new GameNotFoundException(gameNotFoundMessage(gameId));
    }

    public List<SetOfPoints> calculateSetOfPoints(List<DbResult> playerResult, List<DbResult> dataResult) {
        List<SetOfPoints> setOfPointsPerGame = new ArrayList<>();

        Map<String, Integer> dataPointsPerTrashCan = calculateDataPointsPerTrashCan(dataResult);
        int amountTrashCans = dataResult.size();

        for (int i = 0; i < amountTrashCans; i++) {
            String trashCanId = String.valueOf(i + 1);
            DbResult playerResultPerTrashCan = checkPlayerResultPerTrashCan(playerResult, trashCanId);
            DbResult dataResultPerTrashCan = dataResult.get(i);

            int playerPointsPerTrashCanCount = calculatePlayerPointsPerTrashCan(playerResultPerTrashCan, dataResultPerTrashCan);
            int dataPointsCount = dataPointsPerTrashCan.get(trashCanId);

            SetOfPoints setOfPoints = new SetOfPoints(trashCanId, playerPointsPerTrashCanCount, dataPointsCount);
            setOfPointsPerGame.add(setOfPoints);
        }

        return setOfPointsPerGame;
    }

    public Map<String, Integer> calculateDataPointsPerTrashCan(List<DbResult> dataResult) {
        Map<String, Integer> trashIdsPerTrashCan = new HashMap<>();

        for (DbResult dbDataResult : dataResult) {
            String trashCanId = dbDataResult.getTrashCanId();
            int trashIdsPerTrashCanCount = dbDataResult.getTrashIds().size();

            trashIdsPerTrashCan.put(trashCanId, trashIdsPerTrashCanCount);
        }
        return trashIdsPerTrashCan;
    }

    public DbResult checkPlayerResultPerTrashCan(List<DbResult> playerResult, String trashCanId) {
        return playerResult.stream()
                .filter(result -> trashCanId.equals(result.getTrashCanId()))
                .findFirst()
                .orElse(new DbResult());
    }

    public int calculatePlayerPointsPerTrashCan(DbResult playerResult, DbResult dataResult) {
        List<String> playerTrashIds = playerResult.getTrashIds();
        List<String> dataTrashIds = dataResult.getTrashIds();

        if (playerTrashIds == null || dataTrashIds == null) {
            return 0;
        }

        int playerPointsPerTrashCan = 0;

        for (String playerTrashId : playerTrashIds) {
            if (dataTrashIds.contains(playerTrashId)) {
                playerPointsPerTrashCan++;
            }
        }
        return playerPointsPerTrashCan;
    }

    public Player deleteAllGamesResult(String playerId) throws PlayerNotFoundException, GameNotFoundException {
        Player player = repo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerNotFoundMessage(playerId)));

        List<Game> games = player.getGames();
        if (games.isEmpty()) {
            throw new GameNotFoundException(allGamesNotFoundMessage(playerId));
        }

        player.getGames().clear();

        repo.save(player);
        return player;
    }
}
