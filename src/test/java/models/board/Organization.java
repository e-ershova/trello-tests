package models.board;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import models.organization.*;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Organization {
    private String id;
    private String creationMethod;
    private String name;
    private String displayName;
    private String descData;
    private String website;
    private List<Promotions> promotions;
    private EnterpriseJoinRequest enterpriseJoinRequest;
    private Boolean allAdminsEnabled;
    private String desc;
    private String teamType;
    private String idMemberCreator;
    private Boolean invited;
    private List<Invitations> invitations;
    private OrganizationPrefs prefs;
    private List<Memberships> memberships;


}