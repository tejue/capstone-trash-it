package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trash {
    private String id;
    private String name;
    private String image;
    private TrashType trashType;
}
