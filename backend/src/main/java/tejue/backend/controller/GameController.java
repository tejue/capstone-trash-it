package tejue.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tejue.backend.model.Round;
import tejue.backend.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;
    @GetMapping("/{playerId}/rounds")
    public List<Round> getRoundsResults(@PathVariable String playerId) {
        return service.getRoundsResults(playerId);
    }
}
