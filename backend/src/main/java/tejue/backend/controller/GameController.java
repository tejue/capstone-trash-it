package tejue.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tejue.backend.model.Result;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    @GetMapping("/results")
    public List<Result> getAllResults() {
        return service.getAllResults();
    }
}
