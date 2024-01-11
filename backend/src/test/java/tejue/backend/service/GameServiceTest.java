package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.model.Player;
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
    void getRoundsResults_whenPlayerWithId1IsCalled_thenReturnAllRoundsResultsOfPlayer1() {
        //GIVEN
        List<Round> expectedRounds = List.of(new Round(1, 10, 5, 4, 1, 5, 3, 0));
        Player testPlayer = new Player("1", "Jane", expectedRounds);
        gameRepo.save(testPlayer);

        //WHEN & THEN
        when(gameRepo.findById("1")).thenReturn(Optional.of(testPlayer));

        List<Round> actual = gameService.getRoundsResults("1");
        verify(gameRepo).findById("1");
        assertEquals(expectedRounds, actual);
    }


    @Test
    void getRoundsResults_whenNoPlayerFound_thenThrowNoSuchElementException() {
        // GIVEN
        Player testPlayer = new Player("1", "Jane", null);
        gameRepo.save(testPlayer);

        //WHEN & THEN
        when(gameRepo.findById("2")).thenReturn(Optional.of(testPlayer));

        assertThrows(NoSuchElementException.class, () -> gameService.getRoundsResults("1"));
    }
}
