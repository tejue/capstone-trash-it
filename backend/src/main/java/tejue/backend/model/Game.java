package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String id;
    private String mode;
    private List<String> trashCanIds;
    private GameResult gameResult;
}
