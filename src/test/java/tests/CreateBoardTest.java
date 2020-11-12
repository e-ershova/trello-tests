package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.board.TrelloBoard;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBoardTest {

    @Test
    public void test() {
        String boardName = "Scrum board " + RandomStringUtils.random(7, true, true); //используем здесь //генератор случайной строки, чтобы имя каждый раз было уникальным

        RestAssured.baseURI ="https://api.trello.com/1/";

        RequestSpecification mainSpec = new RequestSpecBuilder()
                .addQueryParam("key", "f910238aac21c3539355046cffe2cf07")
                .addQueryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
                .addFilters(Arrays.asList(new RequestLoggingFilter(LogDetail.BODY), new ResponseLoggingFilter(LogDetail.BODY), new AllureRestAssured()))
                .build();

        RequestSpecification boardSpec = new RequestSpecBuilder()
                .addRequestSpecification(mainSpec)
                .setBasePath("/boards")
                .setContentType(ContentType.JSON)
                .addQueryParam("name", boardName)
                .build();


        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        Response createBoardResponse =
                given()
                        .spec(boardSpec)
                    //    .log().all()
                        .post()
                        .then()
                    //    .log().all()
                        .spec(responseSpec)
                        .extract().response();

        TrelloBoard boardFromPostResponse = createBoardResponse.as(TrelloBoard.class);

        TrelloBoard boardFromGetResponse =
                given()
                        .spec(boardSpec)
                     //   .log().all()
                        .pathParam("boardId", boardFromPostResponse.getId())
                        .get("{boardId}")
                        .then()
                      //  .log().body()
                        .spec(responseSpec)
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
