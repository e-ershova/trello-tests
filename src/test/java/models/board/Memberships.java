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

public class Memberships {
    private String id;
    private String idMember;
    private String memberType;
    private Boolean unconfirmed;
    private Boolean deactivated;
}
