package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum TrashType {
    PLASTIC ("plastic"),
    PAPER ("paper"),
    REST ("rest");

    private final String value;
}
