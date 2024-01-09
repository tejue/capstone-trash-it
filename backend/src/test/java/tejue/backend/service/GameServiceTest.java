package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.model.Player;
import tejue.backend.model.Result;
import tejue.backend.model.Round;
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
    void getPlayerResult_whenPlayerWithId1IsCalled_thenReturnAllResultsOfPlayer1() {
        //GIVEN
        List<Round> testRounds = List.of(new Round(1, 10, 5, 4, 1, 5, 3, 0));
        List<Result> expectedResults = List.of(new Result(testRounds));
        Player testPlayer = new Player("1", "Jane", expectedResults);
        gameRepo.save(testPlayer);

        //WHEN & THEN
        when(gameRepo.findById("1")).thenReturn(Optional.of(testPlayer));

        List<Result> actual = gameService.getPlayerResults("1");
        verify(gameRepo).findById("1");
        assertEquals(expectedResults, actual);
    }


    @Test
    void getPlayerResults_whenNoPlayerFound_thenThrowNoSuchElementException() {
        // GIVEN
        Player testPlayer = new Player("1", "Jane", null);
        gameRepo.save(testPlayer);

        //WHEN & THEN
        when(gameRepo.findById("2")).thenReturn(Optional.of(testPlayer));

        try {
            gameService.getPlayerResults("1");
            fail("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException exception) {
            assertEquals("No player found!", exception.getMessage());
        }
    }


    @Test
    void getPlayerResults_whenPlayerFoundButNoResultsFound_thenThrowNoSuchElementException() {
        // GIVEN
        Player testPlayer = new Player("1", "Jane", null);
        gameRepo.save(testPlayer);

        //WHEN & THEN
        when(gameRepo.findById("1")).thenReturn(Optional.of(testPlayer));

        try {
            gameService.getPlayerResults("1");
            fail("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException exception) {
            assertEquals("No results found!", exception.getMessage());
        }
    }
}
