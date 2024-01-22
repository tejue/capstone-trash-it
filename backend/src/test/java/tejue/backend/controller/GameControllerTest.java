package tejue.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tejue.backend.model.*;
import tejue.backend.repo.GameRepo;

import java.util.List;
import java.util.Map;

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
    private GameRepo gameRepo;

    @Test
    void savePlayerResult_whenNewPlayerResult_thenReturnSavedPlayerWithNewResult() throws Exception {
        //GIVEN
        DbResult dbResult = new DbResult("1", List.of("1", "2", "3"));
        List<DbResult> dbPlayerResult = List.of(dbResult);
        Map<String, DbResult> playerResult = Map.of("1", dbResult);
        List<Game> games = List.of(new Game("1", List.of(dbResult), dbPlayerResult, List.of(dbResult)));
        Player player = new Player("1", "Cody Coder", games);
        String playerResultAsJSON = objectMapper.writeValueAsString(playerResult);
        String playerAsJSON = objectMapper.writeValueAsString(player);

        gameRepo.save(player);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerResultAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

    @Test
    void saveDataResult_whenNewGame_thenReturnSavedPlayerWithNewGameResult() throws Exception {
        //GIVEN
        DbResult dbResult = new DbResult("1", List.of("1"));
        List<DbResult> dbGameResult = List.of(dbResult);
        List<Trash> gameData = List.of(new Trash("1", "Trashy", "url", "1", TrashType.PAPER));
        List<Game> games = List.of(new Game("1", List.of(dbResult), List.of(dbResult), dbGameResult));
        Player player = new Player("1", "Cody Coder", games);
        String playerAsJSON = objectMapper.writeValueAsString(player);
        String gameDataAsJSON = objectMapper.writeValueAsString(gameData);

        gameRepo.save(player);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/1/1/dataResult")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gameDataAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

}
