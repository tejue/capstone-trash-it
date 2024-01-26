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

    @PostMapping
    public Player createNewPlayer(@RequestBody PlayerDTO newPlayerDTO) {
        return service.createNewPlayer(newPlayerDTO);
    }

    @GetMapping("/{playerId}")
    public Player getPlayerByIdWithId(@PathVariable String playerId) throws PlayerNotFoundException {
        return service.getPlayerById(playerId);
    }

    @DeleteMapping("/{playerId}")
    public Player deletePlayerById(@PathVariable String playerId) throws PlayerNotFoundException {
        return service.deletePlayerById(playerId);
    }

    @PutMapping("/{playerId}/{gameId}")
    public Player savePlayerResult(@PathVariable String playerId, @PathVariable String gameId, @RequestBody Map<String, DbResult> playerResult) throws PlayerNotFoundException, GameNotFoundException {
        return service.savePlayerResult(playerId, gameId, playerResult);
    }

    @PutMapping("/{playerId}/dataResult")
    public Player saveDataResult(@PathVariable String playerId, @RequestBody List<Trash> gameData) throws PlayerNotFoundException {
        return service.saveDataResult(playerId, gameData);
    }

    @GetMapping("/{playerId}/{gameId}/gameResult")
    public GamePoints getGameResult(@PathVariable String playerId, @PathVariable String gameId) throws PlayerNotFoundException, GameNotFoundException {
        return service.getGameResult(playerId, gameId);
    }

    @GetMapping("/{playerId}/gamesResult")
    public List<GamePoints> getAllGamesResult(@PathVariable String playerId) throws PlayerNotFoundException {
        return service.getAllGamesResult(playerId);
    }

    @PutMapping("/{playerId}/gamesResult")
    public Player deleteAllGamesResult(@PathVariable String playerId) throws PlayerNotFoundException, GameNotFoundException {
        return service.deleteAllGamesResult(playerId);
    }
}
