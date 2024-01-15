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
import tejue.backend.model.TrashCan;
import tejue.backend.model.TrashType;
import tejue.backend.repo.TrashCanRepo;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TrashCanControllerTest {

    private final String BASE_URL = "/api/trashcan";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrashCanRepo testTrashCanRepo;

    @Test
    void getAllTrashCans_whenAllTrashCansAreCalled_thenReturnAllTrashCans() throws Exception {
        List<TrashCan> testTrashCans = List.of(new TrashCan("1", "Paper Can", "blue", "", TrashType.PAPER, List.of("1", "7", "10")));
        String testTrashCanAsJSON = objectMapper.writeValueAsString(testTrashCans);

        Mockito.when(testTrashCanRepo.findAll())
                .thenReturn(testTrashCans);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testTrashCanAsJSON));
    }
}