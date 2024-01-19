package tejue.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tejue.backend.exception.GameNotFoundException;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.*;
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

    @PutMapping("/{playerId}/{gameId}")
    public Player savePlayerResult(@PathVariable String playerId, @PathVariable String gameId, @RequestBody Map<String, DbResult> playerResult) throws PlayerNotFoundException {
        return service.savePlayerResult(playerId, gameId, playerResult);
    }

    @PutMapping("/{playerId}/{gameId}/dataResult")
    public Player saveDataResult(@PathVariable String playerId, @PathVariable String gameId, @RequestBody List<Trash> gameData) throws PlayerNotFoundException {
        return service.saveDataResult(playerId, gameId, gameData);
    }

    @GetMapping("/{playerId}/{gameId}/gameResult")
    public GamePoints getGameResult(@PathVariable String playerId, @PathVariable String gameId) throws PlayerNotFoundException, GameNotFoundException {
        return service.getGameResult(playerId, gameId);
    }


}
