package tests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import models.board.TrelloBoard;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBoardTest extends BaseTest {

    @Test
    public void test() {
        String boardName = "Scrum board " + RandomStringUtils.random(7, true, true); //используем здесь //генератор случайной строки, чтобы имя каждый раз было уникальным

        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectStatusCode(200);
        ResponseSpecification responseSpec = responseBuilder.build();

        Response createBoardResponse =
                given()
                        .spec(getBaseSpecification())
                        .basePath("/boards")
                        .contentType(ContentType.JSON)
                        .queryParam("name", boardName)
                        .log().all()
                        .when()
                        .post()
                        .then()
                        .log().all()
                        .spec(responseSpec)
                        .extract().response();

        TrelloBoard boardFromPostResponse = createBoardResponse.as(TrelloBoard.class);

        TrelloBoard boardFromGetResponse =
                given()
                        .spec(getBaseSpecification())
                        .basePath("/boards")
                        .log().all()
                        .pathParam("boardId", boardFromPostResponse.getId())
                        .when()
                        .get("{boardId}")
                        .then()
                        .log().body()
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
