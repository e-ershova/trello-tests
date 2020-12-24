package tests.card;

import models.board.TrelloBoard;
import models.card.TrelloCard;
import models.list.TrelloList;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class MoveCardToInProgressTest extends BaseTest {

    @Test
    public void moveCardToInProgressList() {

        //создаем доску для списков
        TrelloBoard boardFromPostResponse =
                given()
                        .spec(boardSpec)
                        .queryParam("name", "boardForLists")
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloBoard.class);

        String boardID = boardFromPostResponse.getId();

        //создаем список To Do
        TrelloList toDo =
                given()
                        .spec(listSpec)
                        .queryParam("name", "To Do")
                        .queryParam("idBoard", boardID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloList.class);

        //создаем список In Progress
        TrelloList inProgress =
                given()
                        .spec(listSpec)
                        .queryParam("name", "In Progress")
                        .queryParam("idBoard", boardID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloList.class);

        String toDoListID = toDo.getId();
        String inProgressListID = inProgress.getId();

        //создаем карточку в списке To Do
        TrelloCard cardFromPostResponse =
                given()
                        .spec(cardSpec)
                        .queryParam("name", "cardFromToDoToInProgress")
                        .queryParam("idList", toDoListID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        String cardID = cardFromPostResponse.getId();

        //перемещаем карточку в список In Progress
        TrelloCard updatedCard =
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .queryParam("idList", inProgressListID)
                        .put("{id}")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        //проверяем текущий список карточки
        TrelloCard getListFromUpdatedCard =
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .get("{id}/list")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        //проверяем, что текущий лист карточки = In Progress
        assertThat(getListFromUpdatedCard.getId())
                .as("List of updated card should be In Progress")
                .isEqualTo(inProgressListID);

    }
}
