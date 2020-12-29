package tests;

import models.board.TrelloBoard;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CreateBoardTest extends BaseTest{

    @ParameterizedTest(name = "New Trello Board is created with name {0}")
    @MethodSource("boardNames")
    public void test(String boardName) {

        TrelloBoard boardFromPostResponse =
                given()
                        .spec(boardSpec)
                        .queryParam("name", boardName)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloBoard.class);

        TrelloBoard boardFromGetResponse = getBoard(boardFromPostResponse.getId());
             /*   given()
                        .spec(boardSpec)
                        .pathParam("boardId", boardFromPostResponse.getId())
                        .get("{boardId}")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloBoard.class);*/

        assertThat(boardFromPostResponse.getName())
                .as("Name from post response should be equal to name from get response and name from MethodSource")
                .isEqualTo(boardFromGetResponse.getName())
                .isEqualTo(boardName);

        deleteBoard(boardFromGetResponse.getId());

        given()
                .spec(boardSpec)
                .pathParam("boardId", boardFromPostResponse.getId())
                .get("{boardId}")
                .then()
                .statusCode(404);

        given()
                .spec(organizationSpec)
                .pathParam("organizationId", boardFromPostResponse.getIdOrganization())
                .delete("{organizationId}");

        given()
                .spec(organizationSpec)
                .pathParam("organizationId", boardFromPostResponse.getIdOrganization())
                .get("{organizationId}")
                .then()
                .statusCode(404);

    }

    static Stream<Arguments> boardNames() {

        String random = RandomStringUtils.random(7, false, true); //используем здесь
        // генератор случайной строки, чтобы имя каждый раз было уникальным
        String boardName1 = "Scrum board " + random;
        String boardName2 = "Список покупок " + random;
        String boardName3 = "2128506 " + random;
        String boardName4 = "XTr2LX1CAZUsy0KVxx485WB86J69RUeDeiFYDqK2MjBmzzatSqEMSgZlBrcBXrRX6Hbt9FZeaKm32q8Wwz9rr9Mr5EO9otsqscQ50 " + random;
        //108 символов, если с пробелом и рандомом

        return Stream.of(
                arguments(boardName1),
                arguments(boardName2),
                arguments(boardName3),
                arguments(boardName4)
        );
    }

}
