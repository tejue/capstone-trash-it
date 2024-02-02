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
        ArrayList<Game> games = new ArrayList<>(List.of(new Game("1", List.of(), List.of(dbResult), List.of(dbResult))));
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", games);
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);

        Map<String, DbResult> playerResult = Map.of("1", dbResult);
        String playerResultAsJSON = objectMapper.writeValueAsString(playerResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);
        String playerAsJSON = objectMapper.writeValueAsString(savedPlayer);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + savedPlayer.getId() + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerResultAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

    @Test
    void saveDataResult_whenNewGame_thenReturnSavedPlayerWithNewDataResult() throws Exception {
        //GIVEN
        DbResult dbResult = new DbResult("1", List.of("1"));
        ArrayList<Game> games = new ArrayList<>(List.of(new Game("1", List.of(), List.of(), List.of(dbResult))));
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", new ArrayList<>());
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);

        List<Trash> gameData = List.of(new Trash("1", "Trashy", "url", "1", TrashType.PAPER));
        String gameDataAsJSON = objectMapper.writeValueAsString(gameData);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);

        Player player = new Player(savedPlayer.getId(), "Cody Coder", games);
        String playerAsJSON = objectMapper.writeValueAsString(player);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + savedPlayer.getId() + "/dataResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameDataAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }

    @Test
    void getGameResult_whenCalledWithPlayerAndGameId_thenReturnGamePoints() throws Exception {
        //GIVEN
        DbResult dbResult = new DbResult("1", List.of("1"));
        ArrayList<Game> games = new ArrayList<>(List.of(new Game("1", List.of(), List.of(dbResult), List.of(dbResult))));
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", games);
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);

        GamePoints gamePoints = new GamePoints(1, 1, List.of(new SetOfPoints("1", 1, 1)));
        String gamePointsAsJSON = objectMapper.writeValueAsString(gamePoints);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);
        String playerAsJSON = objectMapper.writeValueAsString(savedPlayer);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedPlayer.getId() + "/1/gameResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(gamePointsAsJSON));
    }

    @Test
    void getAllGamesResult_whenCalledWithPlayerId_thenReturnListOfGamePoints() throws Exception {
        DbResult dbResult = new DbResult("1", List.of("1"));
        ArrayList<Game> games = new ArrayList<>(List.of(new Game("1", List.of(), List.of(dbResult), List.of(dbResult))));
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", games);
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);

        List<GamePoints> allGamesPoints = List.of(new GamePoints(1, 1, List.of(new SetOfPoints("1", 1, 1))));
        String allGamesPointsAsJSON = objectMapper.writeValueAsString(allGamesPoints);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);
        String playerAsJSON = objectMapper.writeValueAsString(savedPlayer);

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedPlayer.getId() + "/gamesResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allGamesPointsAsJSON));
    }

    @Test
    void deleteAllGamesResult_whenCalledWithPlayerId_thenReturnPlayerWithNoGames() throws Exception {
        DbResult dbResult = new DbResult("1", List.of("1"));
        ArrayList<Game> games = new ArrayList<>(List.of(new Game("1", List.of(), List.of(dbResult), List.of(dbResult))));
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", games);
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);

        Player player = new Player(savedPlayer.getId(), "Cody Coder", new ArrayList<>());
        String playerAsJSON = objectMapper.writeValueAsString(player);

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + savedPlayer.getId() + "/gamesResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(playerAsJSON));
    }
}
