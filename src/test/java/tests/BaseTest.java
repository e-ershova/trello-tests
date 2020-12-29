package tests;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.board.TrelloBoard;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

    protected static RequestSpecification authAndLogParams = new RequestSpecBuilder()
            .setBaseUri("https://api.trello.com/1/")
            .addQueryParam("key", "f910238aac21c3539355046cffe2cf07")
            .addQueryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
            .addFilters(Arrays.asList(new RequestLoggingFilter(LogDetail.ALL), new ResponseLoggingFilter(LogDetail.ALL), new AllureRestAssured()))
            .build();

    protected static RequestSpecification boardSpec = new RequestSpecBuilder()
            .addRequestSpecification(authAndLogParams)
            .setBasePath("/boards")
            .setContentType(ContentType.JSON)
            .build();

    protected static RequestSpecification cardSpec = new RequestSpecBuilder()
            .addRequestSpecification(authAndLogParams)
            .setBasePath("/cards")
            .setContentType(ContentType.JSON)
            .build();

    protected static RequestSpecification organizationSpec = new RequestSpecBuilder()
                .addRequestSpecification(authAndLogParams)
                .setBasePath("/organizations")
                .build();

    protected static RequestSpecification listSpec = new RequestSpecBuilder()
            .addRequestSpecification(authAndLogParams)
            .setBasePath("/lists")
            .setContentType(ContentType.JSON)
            .build();

    @Step("Get board with id {0}")
    protected TrelloBoard getBoard(String boardId) {
        return given()
                .spec(boardSpec(boardId))
                .get("{boardId}")
                .then()
                .spec(responseSpecification)
                .extract().as(TrelloBoard.class);
    }

    @Step("Delete board with id {0}")
    protected void deleteBoard(String boardId) {
        given()
                .spec(boardSpec(boardId))
                .delete("{boardId}")
                .then()
                .spec(responseSpecification);
    }

    private RequestSpecification boardSpec(String boardId) {
        return boardSpec
                .pathParam("boardId", boardId);
        
    }



}
