package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.model.Trash;
import tejue.backend.model.TrashType;
import tejue.backend.repo.TrashRepo;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrashServiceTest {

    private final TrashRepo trashRepo = mock(TrashRepo.class);
    private final TrashService trashService = new TrashService(trashRepo);

    @Test
    void getRandomTrashMax_whenCalled_thenReturnListOfTrash() {
        //GIVEN
        List<Trash> expectedTrash = Arrays.asList(
                new Trash("1", "Newspaper", "", TrashType.PAPER),
                new Trash("2", "Plastic Bottle", "", TrashType.PLASTIC),
                new Trash("3", "Yoghurt Cup", "", TrashType.PLASTIC)
        );
        trashRepo.saveAll(expectedTrash);

        when(trashRepo.findAll()).thenReturn(expectedTrash);

        //WHEN
        List<Trash> actual = trashService.getRandomTrashMax();

        //THEN
        verify(trashRepo).findAll();
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() <= 10);
        assertEquals(expectedTrash, actual);
    }
}
