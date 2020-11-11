package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;


public class BaseTest {

    @BeforeAll
    public static void setUp() {

    RestAssured.baseURI ="https://api.trello.com/1/";

}

    public RequestSpecification getBaseSpecification() {

        RequestSpecBuilder mainBuilder = new RequestSpecBuilder();
        mainBuilder.addQueryParam("key", "f910238aac21c3539355046cffe2cf07");
        mainBuilder.addQueryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc");

        return mainBuilder.build();
    }
}
