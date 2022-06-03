package steps;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.ingredients.Ingredients;
import requests.GeneralData;

public class StepToExtractAllIngredients {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Step to extract a body of ingredients list")
    public Ingredients extractOrderListBody (ValidatableResponse response) {

        Ingredients extractedIngredientsListBody = response
                .extract()
                .body()
                .as(Ingredients.class);

        return extractedIngredientsListBody;
    }
}
