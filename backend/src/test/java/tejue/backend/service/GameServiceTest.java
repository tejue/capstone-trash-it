package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.exception.GameNotFoundException;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.*;
import tejue.backend.repo.GameRepo;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {
    private final GameRepo gameRepo = mock(GameRepo.class);
    private final GameService gameService = new GameService(gameRepo);

    String playerId = "1";
    String name = "Cody Coder";
    String gameId = "1";
    DbResult dbResult = new DbResult("1", List.of("1"));
    Trash trash = new Trash("1", "Trashy", "url", "1", TrashType.PAPER);
    Game game = new Game(gameId, List.of(dbResult), List.of(dbResult), List.of(dbResult));
    ArrayList<Game> games = new ArrayList<>(List.of(game));
    Player player = new Player(playerId, name, games);


    @Test
    void playerNotFound_whenCalledWithNonExistingPlayerId_thenReturnStringMessage() {
        //GIVEN
        String expected = "Player with id " + playerId + " not found";

        //WHEN
        String actual = gameService.playerNotFoundMessage(playerId);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void gameNotFound_whenCalledWithNonExistingGameId_thenReturnStringMessage() {
        //GIVEN
        String expected = "Game with id " + gameId + " not found";

        //WHEN
        String actual = gameService.gameNotFoundMessage(gameId);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void allGameNotFound_whenCalledButNoSavedGamesForPlayerId_thenReturnStringMessage() {
        //GIVEN
        String expected = "No games found for player with id " + playerId;

        //WHEN
        String actual = gameService.allGamesNotFoundMessage(gameId);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void saveDataResult_whenNewData_thenReturnSavedPlayerWithNewResult() throws PlayerNotFoundException {
        //GIVEN
        List<Trash> gameData = List.of(trash);
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));
        when(gameRepo.save(player)).thenReturn(player);
//        Player expectedPlayer = new Player(playerId, name, game )
        List<DbResult> expected = List.of(dbResult);

        //WHEN
        Player actualPlayer = gameService.saveDataResult(playerId, gameData);
        List<DbResult> actual = actualPlayer.getGames().getFirst().getDataResult();

        //THEN
        verify(gameRepo).save(player);
        assertEquals(expected, actual);
    }

    @Test
    void saveDataResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        when(gameRepo.findById("Id not existing")).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.saveDataResult(playerId, List.of()));
    }

    @Test
    void transformGameData_whenCalled_thenReturnGameDataTransformedToDbResultWithSameSize() {
        //GIVEN
        List<Trash> gameData = List.of(trash);
        List<DbResult> expected = List.of(dbResult);

        //WHEN
        List<DbResult> actual = gameService.transformGameData(gameData);

        //THEN
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void transformGameData_whenCalled_thenReturnGameDataTransformedToDbResultWithSameContent() {
        //GIVEN
        List<Trash> gameData = List.of(trash);
        List<DbResult> expected = List.of(dbResult);

        //WHEN
        List<DbResult> actual = gameService.transformGameData(gameData);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void savePlayerResult_whenNewPlayerResult_thenReturnSavedPlayerWithNewResult() throws PlayerNotFoundException, GameNotFoundException {
        //GIVEN
        Map<String, DbResult> inputPlayerResult = Map.of("1", dbResult);
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));
        when(gameRepo.save(player)).thenReturn(player);
        List<DbResult> expected = List.of(dbResult);

        //WHEN
        Player actualPlayer = gameService.savePlayerResult(playerId, gameId, inputPlayerResult);
        List<DbResult> actual = actualPlayer.getGames().getFirst().getPlayerResult();

        //THEN
        verify(gameRepo).save(player);
        assertEquals(expected, actual);
    }

    @Test
    void savePlayerResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        when(gameRepo.findById("Id not existing")).thenReturn(Optional.of(player));
        when(gameRepo.findById(gameId)).thenReturn(Optional.empty());

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.savePlayerResult(playerId, "", new HashMap<>()));
    }

    @Test
    void getAllGamesResult_whenAllGamesResultIsCalled_thenReturnListOfGamePoints() throws PlayerNotFoundException {
        //GIVEN
        SetOfPoints setOfPoints = new SetOfPoints("1", 1, 1);
        GamePoints gamePoints = new GamePoints(1, 1, List.of(setOfPoints));
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));
        List<GamePoints> expected = List.of(gamePoints);

        //WHEN
        List<GamePoints> actual = gameService.getAllGamesResult(playerId);

        //THEN
        verify(gameRepo).findById(playerId);
        assertEquals(expected, actual);
    }

    @Test
    void getAllGamesResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        when(gameRepo.findById("Id not existing")).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.getAllGamesResult(playerId));
    }

    @Test
    void getGameResult_whenCalled_thenReturnGamePointsOfActualGame() throws PlayerNotFoundException, GameNotFoundException {
        //GIVEN
        SetOfPoints setOfPoints = new SetOfPoints("1", 1, 1);
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));
        GamePoints expected = new GamePoints(1, 1, List.of(setOfPoints));

        //WHEN
        GamePoints actual = gameService.getGameResult(playerId, gameId);

        //THEN
        verify(gameRepo).findById(player.getId());
        assertEquals(expected, actual);
    }

    @Test
    void getGameResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        when(gameRepo.findById("Id not existing")).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.getGameResult(playerId, ""));
    }

    @Test
    void getGameResult_whenGamesNotFound_thenThrowGameNotFoundException() {
        //GIVEN
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(GameNotFoundException.class, () -> gameService.getGameResult(playerId, ""));
    }

    @Test
    void calculateSetOfPoints_whenCalled_thenReturnListOfSetOfPoints() {
        //GIVEN
        SetOfPoints setOfPoints = new SetOfPoints("1", 1, 1);
        List<SetOfPoints> expected = List.of(setOfPoints);
        List<DbResult> playerResult = List.of(dbResult);
        List<DbResult> dataResult = List.of(dbResult);

        //WHEN
        List<SetOfPoints> actual = gameService.calculateSetOfPoints(playerResult, dataResult);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void calculateDataPointsPerTrashCan_whenCalledWithDataResult_thenReturnAmountOfTrashIdsPerTrashCan() {
        //GIVEN
        List<DbResult> dataResult = List.of(dbResult);
        Map<String, Integer> expected = Map.of("1", 1);

        //WHEN
        Map<String, Integer> actual = gameService.calculateDataPointsPerTrashCan(dataResult);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void checkPlayerResultPerTrashCan_whenTrashCanIdExists_thenReturnTheExisting() {
        //GIVEN
        List<DbResult> playerResult = List.of(dbResult);
        String trashCanId = "1";
        DbResult expected = new DbResult("1", List.of("1"));

        //WHEN
        DbResult actual = gameService.checkPlayerResultPerTrashCan(playerResult, trashCanId);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void checkPlayerResultPerTrashCan_whenTrashCanIdNotExists_thenReturnNew() {
        //GIVEN
        List<DbResult> playerResult = List.of(dbResult);
        String trashCanId = "2";
        DbResult expected = new DbResult();

        //WHEN
        DbResult actual = gameService.checkPlayerResultPerTrashCan(playerResult, trashCanId);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void calculatePlayerPointsPerTrashCan_whenCorrectlySorted_thenReturnPointsForCorrectlySortedTrash() {
        //GIVEN
        int expected = 1;

        //WHEN
        int actual = gameService.calculatePlayerPointsPerTrashCan(dbResult, dbResult);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void calculatePlayerPointsPerTrashCan_whenNotCorrectlySorted_thenReturn0Points() {
        //GIVEN
        int expected = 0;

        //WHEN
        int actual = gameService.calculatePlayerPointsPerTrashCan(dbResult, (new DbResult("2", List.of("5", "6", "7"))));

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteAllGamesResult_whenCalledWithPlayerId_thenReturnPlayerWithNoGamesResult() throws Exception {
        //GIVEN
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));
        Player expected = new Player(playerId, name, new ArrayList<>());

        //WHEN
        Player actual = gameService.deleteAllGamesResult(playerId);

        //THEN
        verify(gameRepo).findById(playerId);
        assertEquals(expected, actual);
    }

    @Test
    void deleteAllGamesResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        when(gameRepo.findById("Id not existing")).thenReturn(Optional.empty());

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.deleteAllGamesResult("Id not existing"));
    }

    @Test
    void deleteAllGamesResult_whenGamesNotFound_thenThrowAllGamesNotFoundException() {
        //GIVEN
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(GameNotFoundException.class, () -> gameService.getGameResult(playerId, ""));
    }
}
