package data;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ingredients.Ingredients;
import model.user.UserCredentialsModel;
import org.apache.commons.lang3.RandomStringUtils;
import requests.ingredients.GetIngredientsRequest;
import steps.StepToExtractAllIngredients;

import java.util.ArrayList;
import java.util.List;

public class GeneratedData {

    @DisplayName("Getter for generating a random username")
    public String getUserName() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    @DisplayName("Getter for generating a random email")
    public String getUserEmail() {
        return RandomStringUtils.randomAlphabetic(10) + "@" + RandomStringUtils.randomAlphabetic(5)
                + "." + RandomStringUtils.randomAlphabetic(3);
    }

    @DisplayName("Getter for generating a random password")
    public String getUserPassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    @DisplayName("Getter for obtaining actual ingredients ID numbers. Actual ingredients are obtained using GET request")
    public List<String> getArrayWithActualIngredients() {

        GetIngredientsRequest getIngredientsRequest = new GetIngredientsRequest();
        StepToExtractAllIngredients stepToExtractAllIngredients = new StepToExtractAllIngredients();
        ValidatableResponse response = getIngredientsRequest.getAllIngredients();
        Ingredients ingredients = stepToExtractAllIngredients.extractOrderListBody(response);

        List<String> ingredientsList =  new ArrayList<>();
        for(int i = 0; i < ingredients.getData().size(); i++) {
            ingredientsList.add(ingredients.getData().get(i).get_id());
        }
        return ingredientsList;
    }

    @DisplayName("Getter for generating a random password")
    public String getDummyIngredientId() {
        String ingredientId = RandomStringUtils.randomAlphabetic(24);
        return ingredientId;
    }
}
