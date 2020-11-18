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


//        "prefs": {
//            "permissionLevel": "private",
//            "orgInviteRestrict": [
//
//            ],
//            "boardInviteRestrict": "any",
//            "externalMembersDisabled": false,
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
//        },
//        "powerUps": [
//
//        ],
//        "products": [
//
//        ],
//        "url": "https://trello.com/userworkspace9810",
//        "logoHash": null,
//        "logoUrl": null,
//        "premiumFeatures": [
//
//        ],
//        "idEnterprise": null,
//        "availableLicenseCount": null,
//        "maximumLicenseCount": null,
//        "ixUpdate": "2",
//        "idBoards": [
//
//        ],
//        "limits": {
//            "orgs": {
//                "totalMembersPerOrg": {
//                    "status": "ok",
//                    "disableAt": 4000,
//                    "warnAt": 3600
//                },
//                "freeBoardsPerOrg": {
//                    "status": "ok",
//                    "disableAt": 10,
//                    "warnAt": 3
//                }
//            }
//        },
//        "credits": [
//
//        ],
//        "billableMemberCount": 1,
//        "memberships": [
//            {
//                "id": "5faeb1ca1702e11b010f3180",
//                "idMember": "5f96caaf85b0d443bbe2b0da",
//                "memberType": "admin",
//                "unconfirmed": false,
//                "deactivated": false
//            }
//        ],
//        "activeBillableMemberCount": 1,
//        "billableCollaboratorCount": 0
//    },