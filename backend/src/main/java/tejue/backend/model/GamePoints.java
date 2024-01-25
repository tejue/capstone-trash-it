package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamePoints {
    private int playerPointsTotal;
    private int dataPointsTotal;
    private List<SetOfPoints> setOfPoints;
}
