package tejue.backend.service;

import org.junit.jupiter.api.Test;
import tejue.backend.model.TrashCan;
import tejue.backend.model.TrashType;
import tejue.backend.repo.TrashCanRepo;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TrashCanServiceTest {

    private final TrashCanRepo trashCanRepo = mock(TrashCanRepo.class);
    private final TrashCanService trashCanService = new TrashCanService(trashCanRepo);

    @Test
    void getAllTrashCans_whenAllTrashCansAreCalled_thenReturnAllTrashCans() {
        //GIVEN
        List<TrashCan> expectedTrashCans = List.of(new TrashCan("1", "Paper Can", "blue", "", TrashType.PAPER, List.of("1", "7", "10")));
        trashCanRepo.saveAll(expectedTrashCans);

        //WHEN & THEN
        when(trashCanRepo.findAll()).thenReturn(expectedTrashCans);

        List<TrashCan> actual = trashCanService.getAllTrashCans();
        verify(trashCanRepo).findAll();
        assertEquals(expectedTrashCans, actual);
    }

    @Test
    void getAllTrashCans_whenNoTrashCanIsSaved_thenReturnEmptyList() {
        //GIVEN
        List<TrashCan> expectedTrashCans = List.of();
        trashCanRepo.saveAll(expectedTrashCans);

        //WHEN & THEN
        when(trashCanRepo.findAll()).thenReturn(Collections.emptyList());

        List<TrashCan> actual = trashCanService.getAllTrashCans();
        verify(trashCanRepo).findAll();
        assertEquals(expectedTrashCans, actual);
    }
}
