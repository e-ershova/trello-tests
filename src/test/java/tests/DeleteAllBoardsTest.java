package tests;

import models.board.TrelloBoard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class DeleteAllBoardsTest extends BaseTest{

    @Test
    public void test() {

        List<TrelloBoard> board =
                given()
                        .when()
                        .spec(authAndLogParams)
                        .pathParam("id", "annachepkasova")
                        .get("/members/{id}/boards")
                        .jsonPath().getList(".", TrelloBoard.class);

        for (TrelloBoard trelloBoard: board) {

            String boardId = trelloBoard.getId();

            given()
                    .spec(authAndLogParams)
                    .basePath("/boards")
                    .pathParam("id", boardId)
                    .delete("{id}");

            given()
                    .spec(authAndLogParams)
                    .basePath("/boards")
                    .pathParam("id", boardId)
                    .get("{id}")
                    .then()
                    .statusCode(404);
        }
    }
}
