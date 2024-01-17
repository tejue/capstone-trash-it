package tejue.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tejue.backend.model.DbResult;
import tejue.backend.model.Game;
import tejue.backend.model.Player;
import tejue.backend.repo.GameRepo;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GameControllerTest {

    private final String BASE_URL = "/api/game";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameRepo testGameRepo;

    @Test
    void getAllGames_whenGamesOfPlayerId1AreCalled_thenReturnAllGamesOfPlayerId1() throws Exception {
        List<DbResult> testDbResult = List.of(new DbResult("1", List.of("1")));
        List<Game> testGames = List.of(new Game("1", testDbResult, testDbResult, testDbResult));
        Player testPlayer = new Player("1", "Jane", testGames);
        String testGamesAsJSON = objectMapper.writeValueAsString(testGames);

        Mockito.when(testGameRepo.findById("1"))
                .thenReturn(Optional.of(testPlayer));

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1/games"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testGamesAsJSON));
    }
}