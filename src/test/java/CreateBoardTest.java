import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import steps.TrelloBoard;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBoardTest {

    @Test
    public void test() {
        String boardName = "Scrum board " + RandomStringUtils.random(7, true, true); //используем здесь //генератор случайной строки, чтобы имя каждый раз было уникальным

        Response createBoardResponse =
                given()
                        .baseUri("https://api.trello.com/1/")
                        .basePath("/boards")
                        .queryParam("key", "f910238aac21c3539355046cffe2cf07")
                        .queryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
                        .queryParam("name", boardName)
                        .contentType(ContentType.JSON)
                        .log().all()
                        .when()
                        .post()
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        TrelloBoard boardFromPostResponse = createBoardResponse.as(TrelloBoard.class);

        TrelloBoard boardFromGetResponse =
                given()
                        .baseUri("https://api.trello.com/1/")
                        .basePath("/boards")
                        .log().all()
                        .pathParam("boardId", boardFromPostResponse.getId())
                        .queryParam("key", "f910238aac21c3539355046cffe2cf07")
                        .queryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
                        .when()
                        .get("{boardId}")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(TrelloBoard.class);

        assertThat(boardFromPostResponse.getName())
                .as("Name from post response should be equal to name from get response").isEqualTo(boardFromGetResponse.getName());
    }
//Создаем новые доски с именем: Scrum board, Agile board, Список покупок
//
//1 создаем доску с именем Scrum board
//2 создаем доску с именем Agile board
//3 создаем доску с именем Список покупок
//4 проверяем (лямбдой?), что есть все три доски (Scrum board, Agile board, Список покупок) и что время их создания не больше минуты назад
//
//хорошо бы, наверное, и цифры проверить, и разной длины названия, например

}
