package tejue.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.DbResult;
import tejue.backend.model.Game;
import tejue.backend.model.Player;
import tejue.backend.repo.GameRepo;
import tejue.backend.service.GameService;

import java.util.List;
import java.util.Map;
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

    @Autowired
    private GameRepo testGameRepo;

    @Test
    void getAllGames_whenGamesOfPlayerId1AreCalled_thenReturnAllGamesOfPlayerId1() throws Exception {
        List<DbResult> testDbResult = List.of(new DbResult("1", List.of("1")));
        List<Game> testGames = List.of(new Game("1", testDbResult, testDbResult, testDbResult));
        Player testPlayer = new Player("1", "Jane", testGames);
        String testGamesAsJSON = objectMapper.writeValueAsString(testGames);

        testGameRepo.save(testPlayer);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1/games"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testGamesAsJSON));
    }

    @Test
    void savePlayerResult_whenNewPlayerResult_thenReturnSavedPlayerWithNewResult() throws Exception {
        //GIVEN
        DbResult testDbResult = new DbResult("1", List.of("1", "2", "3"));
        List<DbResult> testDbPlayerResult = List.of(testDbResult);
        Map<String, DbResult> testPlayerResult = Map.of("1", testDbResult);
        List<Game> testGames = List.of(new Game("1", List.of(testDbResult), testDbPlayerResult, List.of(testDbResult)));
        Player testPlayer = new Player("1", "Jane", testGames);
        String testPlayerResultAsJSON = objectMapper.writeValueAsString(testPlayerResult);
        String testPlayerAsJSON = objectMapper.writeValueAsString(testPlayer);

        testGameRepo.save(testPlayer);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testPlayerResultAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testPlayerAsJSON));
    }
}
