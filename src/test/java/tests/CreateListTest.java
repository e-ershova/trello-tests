package tests;

import models.board.TrelloBoard;
import models.list.TrelloList;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.params.provider.Arguments.arguments;
public class CreateListTest extends BaseTest {

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

    @ParameterizedTest(name = "New Trello List is created with name {0}")
    @MethodSource("listNames")
    public void test(String listName) {

        TrelloList listFromPostResponse =
                given()
                        .spec(listSpec)
                        .queryParam("name", listName)
                        .queryParam("idBoard", boardID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloList.class);
    }

    static Stream<Arguments> listNames() {

        String random = RandomStringUtils.random(7, false, true); //используем здесь
        // генератор случайной строки, чтобы имя каждый раз было уникальным
        String listName1 = "Trello List Name in English " + random;
        String listName2 = "Название на кириллице " + random;
        String listName3 = "21285068800777 " + random;
        String listName4 = "XTr2LX1CAZUsy0KVxx485WB86J69RUeDeiFYDqK2MjBmzzatSqEMSgZlBrcBXrRX6Hbt9FZeaKm32q8Wwz9rr9Mr5EO9otsqscQ50 " + random;
        //108 символов, если с пробелом и рандомом

        return Stream.of(
                arguments(listName1),
                arguments(listName2),
                arguments(listName3),
                arguments(listName4)
        );
    }
}
