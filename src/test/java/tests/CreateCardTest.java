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

public class CreateCardTest extends BaseTest {

    @ParameterizedTest(name = "New Trello Card is created with name {0}")
    @MethodSource("cardNames")
    public void test(String boardName) {

    }

    static Stream<Arguments> cardNames() {

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

        TrelloList listFromPostResponse =
                given()
                        .spec(listSpec)
                        .queryParam("name", "list for cards")
                        .queryParam("idBoard", boardID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloList.class);

        String listID = listFromPostResponse.getId();

        String random = RandomStringUtils.random(7, false, true); //используем здесь
        // генератор случайной строки, чтобы имя каждый раз было уникальным
        String cardName1 = "Scrum board " + random;
        String cardName2 = "Список покупок " + random;
        String cardName3 = "2128506 " + random;
        String cardName4 = "XTr2LX1CAZUsy0KVxx485WB86J69RUeDeiFYDqK2MjBmzzatSqEMSgZlBrcBXrRX6Hbt9FZeaKm32q8Wwz9rr9Mr5EO9otsqscQ50 " + random;
        //108 символов, если с пробелом и рандомом

        return Stream.of(
                arguments(cardName1),
                arguments(cardName2),
                arguments(cardName3),
                arguments(cardName4)
        );
    }
}
