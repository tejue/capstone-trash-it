package tejue.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.DbResult;
import tejue.backend.model.Game;
import tejue.backend.model.Player;
import tejue.backend.service.GameService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;
    @GetMapping("/{playerId}/games")
    public List<Game> getAllGames(@PathVariable String playerId) {
        return service.getAllGames(playerId);
    }

    @PostMapping("/{playerId}/{gameId}")
    public Player savePlayerResult(@PathVariable String playerId, @PathVariable String gameId, @RequestBody Map<String, DbResult> playerResult) throws PlayerNotFoundException {
        return service.savePlayerResult(playerId, gameId, playerResult);
    }

}
