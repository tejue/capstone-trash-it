package tejue.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tejue.backend.model.DbResult;
import tejue.backend.model.Player;
import tejue.backend.model.Round;
import tejue.backend.service.GameService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;
/*    @GetMapping("/{playerId}/rounds")
    public List<Round> getAllRounds(@PathVariable String playerId) {
        return service.getAllRounds(playerId);
    }*/

    @PostMapping("/{playerId}/{gameId}")
    public Player savePlayerResult(@PathVariable String playerId, @PathVariable String gameId, @RequestBody Map<String, DbResult> playerResult) {
        return service.savePlayerResult(playerId, gameId, playerResult);
    }

}
