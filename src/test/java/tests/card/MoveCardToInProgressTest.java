package tests.card;

import models.board.Organization;
import models.board.TrelloBoard;
import models.card.TrelloCard;
import models.list.TrelloList;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import java.util.List;

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

        String toDoListID = toDo.getId();

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

        //получаем текущий список карточки
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

        //проверяем, что в ToDoList нет карточек
        List<TrelloCard> cardsFromToDoList =
                given()
                        .spec(listSpec)
                        .pathParam("id", toDoListID)
                        .get("{id}/cards")
                        .jsonPath().getList(".", TrelloCard.class);

        for (TrelloCard trelloCard: cardsFromToDoList) {

            assertThat(trelloCard.getId())
                    .as("There should be no cards in ToDo list")
                    .isNullOrEmpty();
        }

        //проверяем, что наша карточка в списке InProgress
        List<TrelloCard> cardsFromInProgressList =
                given()
                        .spec(listSpec)
                        .pathParam("id", inProgressListID)
                        .get("{id}/cards")
                        .jsonPath().getList(".", TrelloCard.class);

        for (TrelloCard trelloCard: cardsFromInProgressList) {

            assertThat(trelloCard.getId())
                    .as("The card should be in InProgress list")
                    .isEqualTo(cardID);
        }
    }
}
