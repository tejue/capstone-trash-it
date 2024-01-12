package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrashType {
    PLASTIC ("plastic"),
    PAPER ("paper"),
    REST ("rest");

    private final String value;
}
