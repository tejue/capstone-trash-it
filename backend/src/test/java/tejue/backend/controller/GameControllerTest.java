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
import tejue.backend.model.Player;
import tejue.backend.model.Round;
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
    void getAllRounds_whenRoundResultsOfPlayerId1AreCalled_thenReturnAllRoundResultsOfPlayerId1() throws Exception {
        List<Round> testRounds = List.of(new Round(1, 10, 5, 4, 1, 9, 5, 3, 0));
        Player testPlayer = new Player("1", "Jane", testRounds);
        String testRoundsAsJSON = objectMapper.writeValueAsString(testRounds);

        Mockito.when(testGameRepo.findById("1"))
                .thenReturn(Optional.of(testPlayer));

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1/rounds"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testRoundsAsJSON));
    }
}