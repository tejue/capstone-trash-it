package tejue.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tejue.backend.model.TrashCan;
import tejue.backend.service.TrashCanService;

import java.util.List;

@RestController
@RequestMapping("/api/trashcan")
@RequiredArgsConstructor
public class TrashCanController {

    private final TrashCanService service;

    @GetMapping()
    public List<TrashCan> getAllTrashCans() {
        return service.getAllTrashCans();
    }
}
