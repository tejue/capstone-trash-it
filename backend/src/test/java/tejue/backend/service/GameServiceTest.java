package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.*;
import tejue.backend.repo.GameRepo;

import java.security.Provider;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {
    private final GameRepo gameRepo = mock(GameRepo.class);
    private final GameService gameService = new GameService(gameRepo);

    String playerId = "1";
    String name = "Cody Coder";
    String gameId = "1";

    DbResult dbResult = new DbResult("1", List.of("1", "2", "3"));
    List<DbResult> dbPlayerResult = List.of(dbResult);
    List<DbResult> dbDataResult = List.of(dbResult);
    Map<String, DbResult> playerResult = Map.of("1", dbResult);
    Trash trashA = new Trash("1", "TrashA", "url", "1", TrashType.PAPER);
    Trash trashB = new Trash("2", "TrashB", "url", "1", TrashType.PAPER);
    Trash trashC = new Trash("3", "TrashC", "url", "1", TrashType.PAPER);
    List<Trash> gameData = List.of(trashA, trashB, trashC);
    List<Game> games = List.of(new Game(gameId, dbPlayerResult, List.of(dbResult), List.of(dbResult)));
    Player player = new Player(playerId, name, games);

    @Test
    void getAllGames_whenGamesOfPlayerId1AreCalled_thenReturnAllGamesOfPlayerId1() {
        //GIVEN
        List<DbResult> testDbResult = List.of(new DbResult("1", List.of("1")));
        List<Game> expectedGames = List.of(new Game("1", testDbResult, testDbResult, testDbResult));
        Player testPlayer = new Player("1", "Jane", expectedGames);
        gameRepo.save(testPlayer);

        when(gameRepo.findById("1")).thenReturn(Optional.of(testPlayer));

        //WHEN
        List<Game> actual = gameService.getAllGames("1");

        //THEN
        verify(gameRepo).findById("1");
        assertEquals(expectedGames, actual);
    }


    @Test
    void getAllRounds_whenNoPlayerFound_thenThrowNoSuchElementException() {
        // GIVEN
        Player testPlayer = new Player("1", "Jane", null);
        gameRepo.save(testPlayer);

        when(gameRepo.findById("2")).thenReturn(Optional.of(testPlayer));

        //WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> gameService.getAllGames("1"));
    }

    @Test
    void savePlayerResult_whenNewPlayerResult_thenReturnSavedPlayerWithNewResult() throws PlayerNotFoundException {
        //GIVEN
        DbResult testDbResult = new DbResult("1", List.of("1", "2", "3"));
        List<DbResult> expectedPlayerResult = List.of(testDbResult);
        Map<String, DbResult> testPlayerResult = Map.of("1", testDbResult);
        List<Game> testGames = List.of(new Game("1", expectedPlayerResult, List.of(testDbResult), List.of(testDbResult)));
        Player testPlayer = new Player("1", "Jane", testGames);

        when(gameRepo.findById("1")).thenReturn(Optional.of(testPlayer));
        when(gameRepo.save(testPlayer)).thenReturn(testPlayer);

        //WHEN
        Player actualPlayer = gameService.savePlayerResult("1", "1", testPlayerResult);
        List<DbResult> actual = actualPlayer.getGames().getFirst().getPlayerResult();

        //THEN
        verify(gameRepo).save(testPlayer);
        assertEquals(expectedPlayerResult, actual);
    }

    @Test
    void savePlayerResult_whenPlayerNotFound_thenThrowPlayerNotFoundException() {
        //GIVEN
        Player testPlayer = new Player("1", "Jane", null);

        when(gameRepo.findById("2")).thenReturn(Optional.of(testPlayer));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.savePlayerResult("1", null, null));
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
        Player testPlayer = new Player(playerId, name, null);

        when(gameRepo.findById("2")).thenReturn(Optional.of(testPlayer));

        //WHEN & THEN
        assertThrows(PlayerNotFoundException.class, () -> gameService.saveDataResult(playerId, null, null));
    }

    @Test
    void transformGameData_whenCalled_thenReturnGameDataTransformedToDbResultWithSameSize() {
        //GIVEN
        Trash trashX = new Trash("1", "TrashX", "url", "1", TrashType.PAPER);
        Trash trashXX = new Trash("11", "TrashXX", "url", "1", TrashType.PAPER);
        Trash trashY = new Trash("2", "TrashY", "url", "2", TrashType.RECYCLE);
        Trash trashYY = new Trash("22", "TrashYY", "url", "2", TrashType.RECYCLE);
        Trash trashZ = new Trash("3", "TrashZ", "url", "3", TrashType.REST);
        Trash trashZZ = new Trash("33", "TrashZZ", "url", "3", TrashType.REST);
        List<Trash> inputGameData = List.of(trashX, trashY, trashZ);

        DbResult dbResultX = new DbResult("1", List.of("1"));
        DbResult dbResultY = new DbResult("2", List.of("2"));
        DbResult dbResultZ = new DbResult("3", List.of("3"));

        List<DbResult> expected = List.of(dbResultX, dbResultY, dbResultZ);

        //WHEN
        List<DbResult> actual = gameService.transformGameData(inputGameData);

        //THEN
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void transformGameData_whenCalled_thenReturnGameDataTransformedToDbResultWithSameContent() {
        //GIVEN
        Trash trashX = new Trash("1", "TrashX", "url", "1", TrashType.PAPER);
        Trash trashY = new Trash("2", "TrashY", "url", "2", TrashType.RECYCLE);
        Trash trashZ = new Trash("3", "TrashZ", "url", "3", TrashType.REST);
        List<Trash> inputGameData = List.of(trashX, trashY, trashZ);

        DbResult dbResultX = new DbResult("1", List.of("1"));
        DbResult dbResultY = new DbResult("2", List.of("2"));
        DbResult dbResultZ = new DbResult("3", List.of("3"));

        List<DbResult> expected = List.of(dbResultX, dbResultY, dbResultZ);

        //WHEN
        List<DbResult> actual = gameService.transformGameData(inputGameData);

        //THEN
        assertEquals(expected, actual);
    }
}
