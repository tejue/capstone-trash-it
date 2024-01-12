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
import tejue.backend.model.Trash;
import tejue.backend.model.TrashType;
import tejue.backend.repo.TrashRepo;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TrashControllerTest {

    private final String BASE_URL = "/api/trash";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrashRepo testTrashRepo;

    @Test
    void getAllTrash_whenAllTrashIsCalled_thenReturnAllTrash() throws Exception {
        List<Trash> testTrash = List.of(new Trash("1", "Newspaper", "", TrashType.PAPER));
        String testTrashAsJSON = objectMapper.writeValueAsString(testTrash);

        Mockito.when(testTrashRepo.findAll())
                .thenReturn(testTrash);

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testTrashAsJSON));
    }
}