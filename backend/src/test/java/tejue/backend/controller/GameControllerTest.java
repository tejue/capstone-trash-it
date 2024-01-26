package tejue.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tejue.backend.model.*;
import tejue.backend.repo.GameRepo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    void createNewPlayer_whenNewPlayerCreated_thenSaveNewPlayerWithRandomId() throws Exception {
        //GIVEN
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", new ArrayList<>());
        String playerDTOasJSON = objectMapper.writeValueAsString(playerDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOasJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);
        String playerAsJSON = objectMapper.writeValueAsString(savedPlayer);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedPlayer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

    @Test
    void getPlayerById_whenPlayerCalledWithID_thenReturnPlayerWithId() throws Exception {
        //GIVEN
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", new ArrayList<>());
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);
        String playerAsJSON = objectMapper.writeValueAsString(savedPlayer);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedPlayer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

    @Test
    void deletePlayerById_whenPlayerCalledWithID_thenDeletePlayer() throws Exception {
        //GIVEN
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", new ArrayList<>());
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);
        String playerAsJSON = objectMapper.writeValueAsString(savedPlayer);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + savedPlayer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

    @Test
    void savePlayerResult_whenNewPlayerResult_thenReturnSavedPlayerWithNewResult() throws Exception {
        //GIVEN
        DbResult dbResult = new DbResult("1", List.of("1", "2", "3"));
        List<DbResult> dbPlayerResult = List.of(dbResult);
        Map<String, DbResult> playerResult = Map.of("1", dbResult);
        ArrayList<Game> games = new ArrayList<>(List.of(new Game("1", List.of(dbResult), dbPlayerResult, List.of(dbResult))));
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
        List<DbResult> dbDataResult = List.of(dbResult);
        List<Trash> gameData = List.of(new Trash("1", "Trashy", "url", "1", TrashType.PAPER));
        Game game = new Game("1", List.of(), List.of(), dbDataResult);
        ArrayList<Game> games = new ArrayList<>(List.of(game));
        Player playerZero = new Player("1", "Cody Coder", new ArrayList<>());
        Player player = new Player("1", "Cody Coder", games);
        String playerAsJSON = objectMapper.writeValueAsString(player);
        String gameDataAsJSON = objectMapper.writeValueAsString(gameData);

        gameRepo.save(playerZero);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/1/dataResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameDataAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

    @Test
    void getGameResult_whenCalledWithPlayerAndGameId_thenReturnGamePoints() throws Exception {
        //GIVEN
        DbResult dbResult = new DbResult("1", List.of("1"));
        List<DbResult> dbPlayerResult = List.of(dbResult);
        List<DbResult> dbGameResult = List.of(dbResult);
        Game game = new Game("1", List.of(dbResult), dbPlayerResult, dbGameResult);
        ArrayList<Game> games = new ArrayList<>(List.of(game));
        Player player = new Player("1", "Cody Coder", games);
        String playerAsJSON = objectMapper.writeValueAsString(player);
        GamePoints gamePoints = new GamePoints(1, 1, List.of(new SetOfPoints("1", 1, 1)));
        String gamePointsAsJSON = objectMapper.writeValueAsString(gamePoints);

        gameRepo.save(player);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1/1/gameResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(gamePointsAsJSON));
    }

    @Test
    void getAllGamesResult_whenCalledWithPlayerId_thenReturnListOfGamePoints() throws Exception {
        DbResult dbResult = new DbResult("1", List.of("1"));
        List<DbResult> dbPlayerResult = List.of(dbResult);
        List<DbResult> dbGameResult = List.of(dbResult);
        Game game = new Game("1", List.of(dbResult), dbPlayerResult, dbGameResult);
        ArrayList<Game> games = new ArrayList<>(List.of(game));
        Player player = new Player("1", "Cody Coder", games);
        String playerAsJSON = objectMapper.writeValueAsString(player);
        List<GamePoints> allGamesPoints = List.of(new GamePoints(1, 1, List.of(new SetOfPoints("1", 1, 1))));
        String allGamesPointsAsJSON = objectMapper.writeValueAsString(allGamesPoints);

        gameRepo.save(player);

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1/gamesResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allGamesPointsAsJSON));
    }

    @Test
    void deleteAllGamesResult_whenCalledWithPlayerId_thenReturnPlayerWithNoGames() throws Exception {
        DbResult dbResult = new DbResult("1", List.of("1"));
        List<DbResult> dbPlayerResult = List.of(dbResult);
        List<DbResult> dbGameResult = List.of(dbResult);
        Game game = new Game("1", List.of(dbResult), dbPlayerResult, dbGameResult);
        ArrayList<Game> games = new ArrayList<>(List.of(game));
        Player playerZero = new Player("1", "Cody Coder", new ArrayList<>());
        Player player = new Player("1", "Cody Coder", games);
        String playerAsJSON = objectMapper.writeValueAsString(player);
        String playerZeroAsJSON = objectMapper.writeValueAsString(playerZero);

        gameRepo.save(player);

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/1/gamesResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerZeroAsJSON));
    }
}
