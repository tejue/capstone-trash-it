package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.DbResult;
import tejue.backend.model.Game;
import tejue.backend.model.Player;
import tejue.backend.repo.GameRepo;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {
    private final GameRepo gameRepo = mock(GameRepo.class);
    private final GameService gameService = new GameService(gameRepo);


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
}
