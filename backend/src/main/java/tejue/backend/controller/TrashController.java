package tejue.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tejue.backend.model.Trash;
import tejue.backend.service.TrashService;

import java.util.List;

@RestController
@RequestMapping("/api/trash")
@RequiredArgsConstructor
public class TrashController {

    private final TrashService service;

    @GetMapping()
    public List<Trash> getAllTrash() {
        return service.getRandomTrashMax();
    }
}
