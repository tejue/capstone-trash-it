package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.model.DbResult;
import tejue.backend.model.Game;
import tejue.backend.model.Player;
import tejue.backend.repo.GameRepo;

import java.util.List;
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
}
