package models.card;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import models.Limits;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCard {

    private String id;
    private String name;
}
