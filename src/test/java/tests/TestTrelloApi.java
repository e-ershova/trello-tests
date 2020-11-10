package tests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modules.TrelloList;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TestTrelloApi {
    @Test
    public void createList() {
        String listName = "Купить в Ашане " + RandomStringUtils.random(7, true, true); //используем здесь //генератор случайной строки, чтобы имя списка каждый раз было уникальным
        String boardId = "5d828bcaa54b12677d75a2d8";

        Response createListResponse =
                given()
                        .baseUri("https://api.trello.com/1/")
                        .basePath("/lists")
                        .queryParam("key", "f910238aac21c3539355046cffe2cf07")
                                .queryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
                                .queryParam("name", listName)
                                .queryParam("idBoard", boardId)
                                .contentType(ContentType.JSON)
                                .log().all()
                                .when()
                                .post()
                                .then()
                                .log().all()
                                .statusCode(200)
                                .extract().response();

        TrelloList listFromPostResponse = createListResponse.as(TrelloList.class);

        TrelloList listFromGetresponse =
                given()
                        .baseUri("https://api.trello.com/1/")
                        .basePath("/lists")
                        .log().all()
                        .pathParam("listId", listFromPostResponse.getId())
                        .queryParam("key", "f910238aac21c3539355046cffe2cf07")
                        .queryParam("token", "d0eb3cbf161a54206c2d9b0369a36b240816bc0226b881dba4c4dc33b2b3a2dc")
                        .when()
                        .get("{listId}")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(TrelloList.class);

        assertThat(listFromPostResponse.getName())
                .as("Name from post response should be equal to name from get response").isEqualTo(listFromGetresponse.getName());
    }
}




