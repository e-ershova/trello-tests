package tests.card;
import models.board.TrelloBoard;
import models.card.TrelloCard;
import models.list.TrelloList;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteCardFromDoneTest extends BaseTest {

    @Test
    public void deleteCardFromDoneList() {

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

        //создаем список Done
        TrelloList done =
                given()
                        .spec(listSpec)
                        .queryParam("name", "Done")
                        .queryParam("idBoard", boardID)
                        .post()
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloList.class);

        String doneListID = done.getId();

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
        TrelloCard cardToInProgressList =
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .queryParam("idList", inProgressListID)
                        .put("{id}")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        //получаем текущий список карточки
        TrelloCard getCardList1 =
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .get("{id}/list")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        //проверяем, что текущий лист карточки = In Progress
        assertThat(getCardList1.getId())
                .as("List of updated card should be In Progress")
                .isEqualTo(inProgressListID);

        //перемещаем карточку в список Done
        TrelloCard cardToDoneList =
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .queryParam("idList", doneListID)
                        .put("{id}")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        //получаем текущий список карточки
        TrelloCard getCardList2 =
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .get("{id}/list")
                        .then()
                        .spec(responseSpecification)
                        .extract().as(TrelloCard.class);

        //проверяем, что текущий лист карточки = Done
        assertThat(getCardList2.getId())
                .as("List of updated card should be Done")
                .isEqualTo(doneListID);

        //удаляем карточку
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .delete("{id}")
                        .then()
                        .spec(responseSpecification);

        //проверяем, что карточка удалилась (т.е. ее не найти по ID)
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .get("{id}")
                        .then()
                        .statusCode(404);

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

        //проверяем, что в InProgress нет карточек
        List<TrelloCard> cardsFromInProgressListID =
                given()
                        .spec(listSpec)
                        .pathParam("id", inProgressListID)
                        .get("{id}/cards")
                        .jsonPath().getList(".", TrelloCard.class);

        for (TrelloCard trelloCard: cardsFromInProgressListID) {

            assertThat(trelloCard.getId())
                    .as("There should be no cards in InProgress list")
                    .isNullOrEmpty();
        }

        //проверяем, что в Done нет карточек
        List<TrelloCard> cardsFromDoneListID =
                given()
                        .spec(listSpec)
                        .pathParam("id", doneListID)
                        .get("{id}/cards")
                        .jsonPath().getList(".", TrelloCard.class);

        for (TrelloCard trelloCard: cardsFromDoneListID) {

            assertThat(trelloCard.getId())
                    .as("There should be no cards in Done list")
                    .isNullOrEmpty();
        }
    }
}