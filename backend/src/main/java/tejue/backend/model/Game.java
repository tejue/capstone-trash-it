package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String gameId;
    private List<DbResult> gameResult;
    private List<DbResult> playerResult;
    private List<DbResult> dataResult;
}
