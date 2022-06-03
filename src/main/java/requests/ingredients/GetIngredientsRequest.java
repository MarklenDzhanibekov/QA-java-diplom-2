package requests.ingredients;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import requests.GeneralData;
import static io.restassured.RestAssured.given;

public class GetIngredientsRequest {
    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Request to get all ingredients")
    public ValidatableResponse getAllIngredients () {
        return given()
                .when().get(mainUrl + "ingredients").then();
    }

}


