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

    @DisplayName("Method that's intended for generating new credentials for a new user")
    public UserCredentialsModel dataForUser() {

        String email = RandomStringUtils.randomAlphabetic(10) + "@" + RandomStringUtils.randomAlphabetic(5) + "." + RandomStringUtils.randomAlphabetic(3);
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        UserCredentialsModel userData = new UserCredentialsModel(email, password, name);
        return userData;
    }

    @DisplayName("Getter for generating a random username")
    public String getUserName() {
        String name = RandomStringUtils.randomAlphabetic(10);
        return name;
    }

    @DisplayName("Getter for generating a random email")
    public String getUserEmail() {
        String email = RandomStringUtils.randomAlphabetic(10) + "@" + RandomStringUtils.randomAlphabetic(5)
                + "." + RandomStringUtils.randomAlphabetic(3);
        return email;
    }

    @DisplayName("Getter for generating a random password")
    public String getUserPassword() {
        String password = RandomStringUtils.randomAlphabetic(10);
        return password;
    }

    @DisplayName("Getter for obtaining actual ingredients ID numbers. Actual ingredients are obtained using GET request")
    public List<String> getArrayWithActualIngredients() {

        GetIngredientsRequest getIngredientsRequest = new GetIngredientsRequest();
        StepToExtractAllIngredients stepToExtractAllIngredients = new StepToExtractAllIngredients();
        ValidatableResponse response = getIngredientsRequest.getAllIngredients();
        Ingredients ingredients = stepToExtractAllIngredients.extractOrderListBody(response);

        List<String> ingredientsList = new ArrayList<String>();
        for(int i = 0; i < ingredients.getData().size(); i++) {
            ingredientsList.add(ingredients.getData().get(0+i).get_id());
        }
        return ingredientsList;
    }

    @DisplayName("Getter for generating a random password")
    public String getDummyIngredientId() {
        String ingredientId = RandomStringUtils.randomAlphabetic(24);
        return ingredientId;
    }
}
