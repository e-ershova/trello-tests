package tests;

import models.board.Organization;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
public class DeleteAllOrganizationsList extends BaseTest {

    @Test
    public void test() {

        List<Organization> org =
                given()
                        .when()
                        .spec(authAndLogParams)
                        .pathParam("id", "annachepkasova")
                        .get("/members/{id}/organizations")
                        .jsonPath().getList(".", Organization.class);

        for (Organization organization: org) {

            String orgId = organization.getId();

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
