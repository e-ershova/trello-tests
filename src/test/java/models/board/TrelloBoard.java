package models.board;
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
public class TrelloBoard {
    private String id;
    private String name;
    private Boolean closed;
    private String desc;
    private String descData;
    private String idOrganization;
    private String idEnterprise;
    private Boolean pinned;
    private String shortLink;
    private String url;
    private String shortUrl;
    private LabelNames labelNames;
    private Organization organization;
    private Limits limits;

}
