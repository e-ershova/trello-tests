package tests;

import models.board.TrelloBoard;
import models.card.TrelloCard;
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
    public void test(String cardName, String listID) {

        TrelloCard cardFromPostResponse =
                given()
                        .spec(cardSpec)
                        .queryParam("name", cardName)
                        .queryParam("idList", listID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        TrelloCard cardFromGetResponse =
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardFromPostResponse.getId())
                        .get("{id}")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        assertThat(cardFromPostResponse.getName())
                .as("Name from post response should be equal to name from get response and name from MethodSource")
                .isEqualTo(cardFromGetResponse.getName())
                .isEqualTo(cardName);
    }

    static Stream<Arguments> cardNames() {

        String randomBoard = RandomStringUtils.random(7, false, true);
        TrelloBoard boardFromPostResponse =
                given()
                        .spec(boardSpec)
                        .queryParam("name", "boardForListWithCards" + randomBoard)
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
        String cardName1 = "Scrum board card" + random;
        String cardName2 = "Карточка списка покупок " + random;
        String cardName3 = "2128506 6470" + random;
        String cardName4 = "XTr2LX1CAZUsy0KVxx485WB86J69RUeDeiFYDqK2MjBmzzatSqEMSgZlBrcBXrRX6Hbt9FZeaKm32q8Wwz9rr9Mr5EO9otsqscQ50 " + random;
        //108 символов, если с пробелом и рандомом

        return Stream.of(
                arguments(cardName1, listID),
                arguments(cardName2, listID),
                arguments(cardName3, listID),
                arguments(cardName4, listID)
        );
    }
}
