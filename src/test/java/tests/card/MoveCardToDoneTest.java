package tests.card;
import models.board.TrelloBoard;
import models.card.TrelloCard;
import models.list.TrelloList;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class MoveCardToDoneTest extends BaseTest  {

    @Test
    public void moveCardToDoneList() {

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
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .queryParam("idList", inProgressListID)
                        .put("{id}")
                        .then()
                        .spec(responseSpecification);

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
                given()
                        .spec(cardSpec)
                        .pathParam("id", cardID)
                        .queryParam("idList", doneListID)
                        .put("{id}")
                        .then()
                        .spec(responseSpecification);

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
    }

}
