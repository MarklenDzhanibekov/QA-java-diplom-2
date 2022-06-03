package order;
import data.GeneratedData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ingredients.IngredientsListModel;
import org.junit.*;
import requests.order.GetOrdersRequest;
import requests.order.PostOrdersRequest;
import requests.user.PostUserRequest;
import steps.*;
import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest {
    GeneratedData newGeneratedData = new GeneratedData();
    String password = newGeneratedData.getUserPassword();
    String email = newGeneratedData.getUserEmail();
    String name = newGeneratedData.getUserName();
    StepToGetTokens stepToGetTokens = new StepToGetTokens();
    IngredientsListModel ingredients = new IngredientsListModel();
    String accessTokenFromResponse;
    PostUserRequest postUserRequest = new PostUserRequest();
    PostOrdersRequest postOrdersRequest = new PostOrdersRequest();
    GetOrdersRequest getOrdersRequest = new GetOrdersRequest();

    @After
    @DisplayName("delete all users that were created for tests purpose within current test")
    public void deleteAllUsersCreatedInLoginTestClass() {
        StepToDeleteUser stepToDeleteUser = new StepToDeleteUser();
        stepToDeleteUser.deletionUser(accessTokenFromResponse);
    };

    @Test
    @DisplayName("Get a specific order with authorization")
    public void getOrderWithAuthorization() {

        //set up a needed set of ingredients
        ingredients.setIngredients(
                new String[]{newGeneratedData.getArrayWithActualIngredients().get(7),
                        newGeneratedData.getArrayWithActualIngredients().get(2),
                        newGeneratedData.getArrayWithActualIngredients().get(3)});

        //a new user creating
        ValidatableResponse response1 = postUserRequest.createNewUser(email, password, name);

        //extract the user's access token
        accessTokenFromResponse = stepToGetTokens.getAccessToken(response1);

        //creating a new order using recently prepared ingredients and an extracted access token
        postOrdersRequest.createNewOrder(ingredients, accessTokenFromResponse);

        //execution of GET request for a specific order using a user's access token
        ValidatableResponse response2 = getOrdersRequest.getOrder(accessTokenFromResponse);

        //assertions section
        response2.assertThat().statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Get a specific order without authorization")
    public void getOrderWithoutAuthorization() {

        //set up a needed set of ingredients
        ingredients.setIngredients(
                new String[]{newGeneratedData.getArrayWithActualIngredients().get(7),
                        newGeneratedData.getArrayWithActualIngredients().get(2),
                        newGeneratedData.getArrayWithActualIngredients().get(3)});

        //a new user creating
        ValidatableResponse response1 = postUserRequest.createNewUser(email, password, name);

        stepToGetTokens.getAccessToken(response1);

        //create empty access token
        accessTokenFromResponse = "";

        //creating a new order using recently prepared ingredients and an extracted access token
        postOrdersRequest.createNewOrder(ingredients, accessTokenFromResponse);

        //execution of GET request for a specific order using a user's access token
        ValidatableResponse response2 = getOrdersRequest.getOrder(accessTokenFromResponse);

        //assertions section
        response2.assertThat().statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

}

