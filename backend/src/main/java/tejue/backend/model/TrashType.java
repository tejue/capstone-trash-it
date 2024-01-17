package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrashType {
    PAPER("paper"),
    RECYCLE("recyclable"),
    REST("rest");

    private final String value;
}
