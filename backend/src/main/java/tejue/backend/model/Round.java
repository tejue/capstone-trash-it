package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Round {
      private int noOfRound;
      private int garbageTotal;
      private int garbageTotalPlastic;
      private int garbageTotalPaper;
      private int garbageTotalRest;
      private int trashedPlastic;
      private int trashedPaper;
      private int trashedRest;
}
