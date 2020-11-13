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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CreateBoardTest {

    @ParameterizedTest(name = "New Trello Board is created with name {0}")
    @MethodSource("boardNames")
    public void test(String boardName) {

        RestAssured.baseURI = "https://api.trello.com/1/";

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
                        .post()
                        .then()
                        .spec(responseSpec)
                        .extract().response();

        TrelloBoard boardFromPostResponse = createBoardResponse.as(TrelloBoard.class);

        TrelloBoard boardFromGetResponse =
                given()
                        .spec(boardSpec)
                        .pathParam("boardId", boardFromPostResponse.getId())
                        .get("{boardId}")
                        .then()
                        .spec(responseSpec)
                        .extract().as(TrelloBoard.class);

        assertThat(boardFromPostResponse.getName())
                .as("Name from post response should be equal to name from get response").isEqualTo(boardFromGetResponse.getName());
    }

    static Stream<Arguments> boardNames() {

        String random = RandomStringUtils.random(7, false, true); //используем здесь
        // генератор случайной строки, чтобы имя каждый раз было уникальным
        String boardName1 = "Scrum board " + random;
        String boardName2 = "Список покупок " + random;
        String boardName3 = "2128506 " + random;
        String boardName4 = "XTr2LX1CAZUsy0KVxx485WB86J69RUeDeiFYDqK2MjBmzzatSqEMSgZlBrcBXrRX6Hbt9FZeaKm32q8Wwz9rr9Mr5EO9otsqscQ50 " + random; //101 символ

        return Stream.of(
                arguments(boardName1),
                arguments(boardName2),
                arguments(boardName3),
                arguments(boardName4)
        );
    }

}
