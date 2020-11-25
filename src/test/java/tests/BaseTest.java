package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Arrays;

public class BaseTest {

    protected ResponseSpecification responseSpecification;
    protected RequestSpecification authAndLogParams;
    protected RequestSpecification boardSpec;
    protected RequestSpecification organizationSpec;
    protected RequestSpecification listSpec;

    public BaseTest() {


        RestAssured.baseURI = "https://api.trello.com/1/";

        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

        authAndLogParams = new RequestSpecBuilder()
                .addQueryParam("key", "f910238aac21c3539355046cffe2cf07")
                .addQueryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
                .addFilters(Arrays.asList(new RequestLoggingFilter(LogDetail.BODY), new ResponseLoggingFilter(LogDetail.BODY), new AllureRestAssured()))
                .build();

        boardSpec = new RequestSpecBuilder()
                .addRequestSpecification(authAndLogParams)
                .setBasePath("/boards")
                .setContentType(ContentType.JSON)
                .build();

        organizationSpec = new RequestSpecBuilder()
                .addRequestSpecification(authAndLogParams)
                .setBasePath("/organizations")
                .build();

        listSpec = new RequestSpecBuilder()
                .addRequestSpecification(authAndLogParams)
                .setBasePath("/lists")
                .setContentType(ContentType.JSON)
                .build();
    }

}
