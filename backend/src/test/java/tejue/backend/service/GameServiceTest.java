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
    void getAllRounds_whenRoundResultsOfPlayerId1AreCalled_thenReturnAllRoundResultsOfPlayerId1() {
        //GIVEN
        List<Round> expectedRounds = List.of(new Round(1, 10, 5, 4, 1, 9, 5, 3, 0));
        Player testPlayer = new Player("1", "Jane", expectedRounds);
        gameRepo.save(testPlayer);

        when(gameRepo.findById("1")).thenReturn(Optional.of(testPlayer));

        //WHEN
        List<Round> actual = gameService.getAllRounds("1");

        //THEN
        verify(gameRepo).findById("1");
        assertEquals(expectedRounds, actual);
    }


    @Test
    void getAllRounds_whenNoPlayerFound_thenThrowNoSuchElementException() {
        // GIVEN
        Player testPlayer = new Player("1", "Jane", null);
        gameRepo.save(testPlayer);

        when(gameRepo.findById("2")).thenReturn(Optional.of(testPlayer));

        //WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> gameService.getAllRounds("1"));
    }
}
