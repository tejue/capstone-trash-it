package tejue.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tejue.backend.model.ErrorMessage;
import tejue.backend.model.Player;
import tejue.backend.model.PlayerDTO;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GlobalControllerAdviceTest {

    private final String BASE_URL = "/api/game";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void handlePlayerNotFoundException_whenPlayerNotFoundExceptionIsThrown_thenReturnErrorMessage() throws Exception {
        //GIVEN
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST + ": An Error occurred - Player could not be found!");
        String errorMessageAsJSON = objectMapper.writeValueAsString(errorMessage);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(errorMessageAsJSON));
    }

    @Test
    void handleGameNotFoundException_whenGameNotFoundExceptionIsThrown_thenReturnErrorMessage() throws Exception {
        //GIVEN
        PlayerDTO playerDTO = new PlayerDTO("Cody Coder", new ArrayList<>());
        String playerDTOAsJSON = objectMapper.writeValueAsString(playerDTO);
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST + ": An Error occurred - Player could not be found!");
        String errorMessageAsJSON = objectMapper.writeValueAsString(errorMessage);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Player savedPlayer = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class);

        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedPlayer.getId() + "1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(errorMessageAsJSON));
    }
}