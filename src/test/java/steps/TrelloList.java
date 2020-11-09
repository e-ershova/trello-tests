package steps;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)

public class TrelloList {

    private String id;
    private String name;
    private Boolean closed;
    private Integer pos;
    private String idBoard;
    private Limits limits;

}
