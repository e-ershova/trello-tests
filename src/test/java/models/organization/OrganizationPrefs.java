package models.organization;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)

public class OrganizationPrefs {

    private String permissionLevel;
    private List<OrgInviteRestrict> orgInviteRestrict;
    private String boardInviteRestrict;
    private Boolean externalMembersDisabled;
    private String associatedDomain;
    private Integer googleAppsVersion;
    private BoardVisibilityRestrict boardVisibilityRestrict;



//            "associatedDomain": null,
//            "googleAppsVersion": 1,
//            "boardVisibilityRestrict": {
//                "private": "org",
//                "org": "org",
//                "enterprise": "org",
//                "public": "org"
//            },
//            "boardDeleteRestrict": {
//                "private": "org",
//                "org": "org",
//                "enterprise": "org",
//                "public": "org"
//            },
//            "attachmentRestrictions": null
}
