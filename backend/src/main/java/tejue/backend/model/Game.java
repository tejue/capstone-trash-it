package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String gameId;
    private List<DbResult> gameResult = List.of();
    private List<DbResult> playerResult = List.of();
    private List<DbResult> dataResult = List.of();
}
