package models.board;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class LabelNames {
    private String green;
    private String yellow;
    private String orange;
    private String red;
    private String purple;
    private String blue;
    private String sky;
    private String lime;
    private String pink;
    private String black;

}

