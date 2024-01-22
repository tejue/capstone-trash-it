package tejue.backend.service;

import org.junit.jupiter.api.Test;
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
    List<DbResult> dbPlayerResult = List.of(dbResult);
    List<DbResult> dbDataResult = List.of(dbResult);
    List<Trash> gameData = List.of(new Trash("1", "TrashA", "url", "1", TrashType.PAPER));
    List<Game> games = List.of(new Game(gameId, List.of(dbResult), dbPlayerResult, dbDataResult));
    Player player = new Player(playerId, name, games);

    @Test
    void getAllGames_whenGamesOfPlayerId1AreCalled_thenReturnAllGamesOfPlayerId1() {
        //GIVEN
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));

        List<Game> expected = List.of(new Game(gameId, List.of(dbResult), dbPlayerResult, dbDataResult));

        //WHEN
        List<Game> actual = gameService.getAllGames("1");

        //THEN
        verify(gameRepo).findById("1");
        assertEquals(expected, actual);
    }


    @Test
    void getAllRounds_whenNoPlayerFound_thenThrowNoSuchElementException() {
        // GIVEN
        when(gameRepo.findById("2")).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> gameService.getAllGames(playerId));
    }

    @Test
    void savePlayerResult_whenNewPlayerResult_thenReturnSavedPlayerWithNewResult() throws PlayerNotFoundException {
        //GIVEN
        Map<String, DbResult> inputPlayerResult = Map.of("1", dbResult);

        when(gameRepo.findById(gameId)).thenReturn(Optional.of(player));
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
        when(gameRepo.findById("2")).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.savePlayerResult(playerId, null, null));
    }

    @Test
    void saveDataResult_whenNewData_thenReturnSavedPlayerWithNewResult() throws PlayerNotFoundException {
        //GIVEN
        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));
        when(gameRepo.save(player)).thenReturn(player);

        List<DbResult> expectedDataResult = List.of(dbResult);

        //WHEN
        Player actualPlayer = gameService.saveDataResult(playerId, gameId, gameData);
        List<DbResult> actual = actualPlayer.getGames().getFirst().getDataResult();

        //THEN
        verify(gameRepo).save(player);
        assertEquals(expectedDataResult, actual);
    }

    @Test
    void saveDataResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        when(gameRepo.findById("2")).thenReturn(Optional.of(player));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.saveDataResult(playerId, null, null));
    }

    @Test
    void transformGameData_whenCalled_thenReturnGameDataTransformedToDbResultWithSameSize() {
        //GIVEN
        List<DbResult> expected = List.of(dbResult);

        //WHEN
        List<DbResult> actual = gameService.transformGameData(gameData);

        //THEN
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void transformGameData_whenCalled_thenReturnGameDataTransformedToDbResultWithSameContent() {
        //GIVEN
        List<DbResult> expected = List.of(dbResult);

        //WHEN
        List<DbResult> actual = gameService.transformGameData(gameData);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getAllGamesResult_whenAllGamesResultIsCalled_thenReturnListOfGamePoints() throws PlayerNotFoundException {
        //GIVEN
        SetOfPoints setOfPointsA = new SetOfPoints("1", 1, 1);
        GamePoints gamePointsA = new GamePoints(1, 1, List.of(setOfPointsA));

        when(gameRepo.findById(playerId)).thenReturn(Optional.of(player));

        List<GamePoints> expected = List.of(gamePointsA);

        //WHEN
        List<GamePoints> actual = gameService.getAllGamesResult(player.getId());

        //THEN
        verify(gameRepo).findById(player.getId());
        assertEquals(expected, actual);
    }

    @Test
    void getAllGamesResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        Player testPlayer = new Player(playerId, name, null);

        when(gameRepo.findById("2")).thenReturn(Optional.of(testPlayer));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.saveDataResult(playerId, null, null));
    }

    @Test
    void calculateSetOfPoints_whenCalled_thenReturnListOfSetOfPoints() {
        //GIVEN
        SetOfPoints setOfPointsA = new SetOfPoints("1", 1, 1);
        List<SetOfPoints> expected = List.of(setOfPointsA);

        //WHEN
        List<SetOfPoints> actual = gameService.calculateSetOfPoints(dbPlayerResult, dbDataResult);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void calculateDataPointsPerTrashCan_whenCalledWithDataResult_thenReturnAmountOfTrashIdsPerTrashCan() {
        //GIVEN
        Map<String, Integer> expected = Map.of("1", 1);

        //WHEN
        Map<String, Integer> actual = gameService.calculateDataPointsPerTrashCan(dbDataResult);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getPlayerResultPerTrashCan_whenLengthOfPlayerResultEqualsTrashCanIndex_thenReturnDbResult() {
        //GIVEN
        DbResult expected = dbResult;

        //WHEN
        DbResult actual = gameService.getPlayerResultPerTrashCan(dbPlayerResult, 0);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getPlayerResultPerTrashCan_whenLengthOfPlayerResultIsSmallerThanTrashCanIndex_thenReturnNewEmptyDbResult() {
        //GIVEN
        DbResult expected = new DbResult(null, null);

        //WHEN
        DbResult actual = gameService.getPlayerResultPerTrashCan(dbPlayerResult, 1);

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
}
