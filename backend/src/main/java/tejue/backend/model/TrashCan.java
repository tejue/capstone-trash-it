package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrashCan {
    private String id;
    private String name;
    private String color;
    private String image;
    private List<String> trashIds;
}
