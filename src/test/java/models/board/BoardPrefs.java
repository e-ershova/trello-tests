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
public class BoardPrefs {
    private String permissionLevel;
    private Boolean hideVotes;
    private String voting;
    private String comments;
    private String invitations;
    private Boolean selfJoin;
    private Boolean cardCovers;
    private Boolean isTemplate;
    private String cardAging;
    private Boolean calendarFeedEnabled;
    private String background;
    private String backgroundImage;
    private Boolean backgroundTile;
    private String backgroundBrightness;
    private String backgroundColor;
    private String backgroundBottomColor;
    private String backgroundTopColor;
    private Boolean canBePublic;
    private Boolean canBeEnterprise;
    private Boolean canBeOrg;
    private Boolean canBePrivate;
    private Boolean canInvite;

}
