package models.list;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import models.Limits;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonIgnoreProperties(ignoreUnknown = true)

public class TrelloList {

    private String id;
    private String name;
    private Boolean closed;
    private Integer pos;
    private String idBoard;
    private Limits limits;

}
