package tejue.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("trashCan")
public class TrashCan {
    @Id
    private String id;
    private String name;
    private String color;
    private String image;
    private TrashType trashType;
    private List<String> trashIds;
}
