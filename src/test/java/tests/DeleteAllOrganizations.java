package tests;

import models.board.Organization;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static io.restassured.RestAssured.given;
public class DeleteAllOrganizations extends BaseTest {

    @Test
    public void test() {

        Organization[] organizations =
                given()
                        .when()
                        .spec(authAndLogParams)
                        .pathParam("id", "annachepkasova")

                        .get("/members/{id}/organizations")

                        .as(Organization[].class);

        for (int i = 0; i < Array.getLength(organizations); i++) {

            String orgId = organizations[i].getId();

            given()
                    .spec(authAndLogParams)
                    .basePath("/organizations")
                    .pathParam("organizationId", orgId)
                    .delete("{organizationId}");

            given()
                    .spec(authAndLogParams)
                    .basePath("/organizations")
                    .pathParam("organizationId", orgId)
                    .get("{organizationId}")
                    .then()
                    .statusCode(404);
        }
    }
}
