package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import models.board.TrelloBoard;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBoardTest extends BaseTest {

    @Test
    public void test() {
        String boardName = "Scrum board " + RandomStringUtils.random(7, true, true); //используем здесь //генератор случайной строки, чтобы имя каждый раз было уникальным

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://api.trello.com/1/");
        builder.setBasePath("/boards");
        builder.addParam("key", "f910238aac21c3539355046cffe2cf07");
        builder.addParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc");
        builder.addParam("name", boardName);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();

        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectStatusCode(200);
        ResponseSpecification responseSpec = responseBuilder.build();

        Response createBoardResponse =
                given()
                        .spec(requestSpec)
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
                        .baseUri("https://api.trello.com/1/")
                        .basePath("/boards")
                        .log().all()
                        .pathParam("boardId", boardFromPostResponse.getId())
                        .queryParam("key", "f910238aac21c3539355046cffe2cf07")
                        .queryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
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
