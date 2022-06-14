package requests.order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.ingredients.IngredientsListModel;
import requests.GeneralData;
import static io.restassured.RestAssured.*;


public class PostOrdersRequest {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Request to create a new order")
    public ValidatableResponse createNewOrder(String ingredientId, String userAccessToken) {

        return given().header("Content-type", "application/json")
                .and().header("Authorization", userAccessToken)
                .and().body(ingredientId)
                .when().post(mainUrl + "orders").then();
    }

    @Step("Request to create a new order using ingredients model")
    public ValidatableResponse createNewOrder(IngredientsListModel ingredientsListModel, String userAccessToken) {

        return given().header("Content-type", "application/json")
                .and().header("Authorization", userAccessToken)
                .and().body(ingredientsListModel)
                .when().post(mainUrl + "orders").then();
    }

    @Step("Request to create a new order without ingredients")
    public ValidatableResponse createNewOrderWithoutIngredients(String userAccessToken) {

        return given().header("Content-type", "application/json")
                .and().header("Authorization", userAccessToken)
                .when().post(mainUrl + "orders").then();
    }

}
