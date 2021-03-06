package tests;

import models.board.TrelloBoard;
import models.list.TrelloList;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
public class CreateListTest extends BaseTest {



    @ParameterizedTest(name = "New Trello List is created with name {0}")
    @MethodSource("listNames")
    public void test(String listName, String boardID) {

        TrelloList listFromPostResponse =
                given()
                        .spec(listSpec)
                        .queryParam("name", listName)
                        .queryParam("idBoard", boardID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloList.class);

        TrelloList listFromGetResponse =
                given()
                        .spec(listSpec)
                        .pathParam("id", listFromPostResponse.getId())
                        .get("{id}")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloList.class);

        assertThat(listFromPostResponse.getName())
                .as("Name from post response should be equal to name from get response and name from MethodSource")
                .isEqualTo(listFromGetResponse.getName())
                .isEqualTo(listName);

    }

    static Stream<Arguments> listNames() {

        String randomBoard = RandomStringUtils.random(7, false, true);
        TrelloBoard boardFromPostResponse =
                given()
                        .spec(boardSpec)
                        .queryParam("name", "boardForLists" + randomBoard)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloBoard.class);
        String boardID = boardFromPostResponse.getId();

        String random = RandomStringUtils.random(7, false, true); //используем здесь
        // генератор случайной строки, чтобы имя каждый раз было уникальным
        String listName1 = "Trello List Name in English " + random;
        String listName2 = "Название на кириллице " + random;
        String listName3 = "21285068800777 " + random;
        String listName4 = "XTr2LX1CAZUsy0KVxx485WB86J69RUeDeiFYDqK2MjBmzzatSqEMSgZlBrcBXrRX6Hbt9FZeaKm32q8Wwz9rr9Mr5EO9otsqscQ50 " + random;
        //108 символов, если с пробелом и рандомом

        return Stream.of(
                arguments(listName1, boardID),
                arguments(listName2, boardID),
                arguments(listName3, boardID),
                arguments(listName4, boardID)
        );
    }
}
