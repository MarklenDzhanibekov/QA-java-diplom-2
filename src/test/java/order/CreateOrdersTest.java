package order;
import data.GeneratedData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ingredients.IngredientsListModel;
import model.orders.CreatedOrder;
import org.junit.*;
import requests.order.PostOrdersRequest;
import requests.user.DeleteUserRequest;
import requests.user.PostUserRequest;
import steps.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrdersTest {
    GeneratedData newGeneratedData = new GeneratedData();
    String dummyIngredientId1 = newGeneratedData.getDummyIngredientId();
    String emptyIngredients = "";
    String password = newGeneratedData.getUserPassword();
    String email = newGeneratedData.getUserEmail();
    String name = newGeneratedData.getUserName();
    StepToGetTokens stepToGetTokens = new StepToGetTokens();
    IngredientsListModel ingredients = new IngredientsListModel();
    IngredientsListModel wrongIngredients = new IngredientsListModel();
    String accessTokenFromResponse;
    PostUserRequest postUserRequest = new PostUserRequest();
    PostOrdersRequest postOrdersRequest = new PostOrdersRequest();
    StepToExtractCreatedOrder stepToExtractCreatedOrder = new StepToExtractCreatedOrder();
    ValidatableResponse response1, response2;
    CreatedOrder extractedCreatedOrder;

    @Before
    @DisplayName("set up ingredients set, creating a new user, extracting a user's token and creating " +
            "a new order for the upcoming tests ")
    public void executedBeforeEach() {

        //set up a needed set of ingredients
        ingredients.setIngredients(
                new String[]{newGeneratedData.getArrayWithActualIngredients().get(7),
                        newGeneratedData.getArrayWithActualIngredients().get(2),
                        newGeneratedData.getArrayWithActualIngredients().get(3)});

        //a new user creating
        response1 = postUserRequest.createNewUser(email, password, name);

        //extract the user's access token
        accessTokenFromResponse = stepToGetTokens.getAccessToken(response1);

        //creating a new order using recently prepared ingredients and an extracted access token
        response2 = postOrdersRequest.createNewOrder(ingredients, accessTokenFromResponse);

        //Extract an order body for further assertions
        extractedCreatedOrder = stepToExtractCreatedOrder.extractCreatedOrder(response2);
    }

    @After
    @DisplayName("delete all users that were created for tests purpose within current test")
    public void deleteAllUsersCreatedInLoginTestClass() {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.deleteUser(accessTokenFromResponse);
    }

    @Test
    @DisplayName("Create an order with ingredients")
    public void createOrderWithAuthorization() {
        //Assertions section
        // check if the response and 'success' messages are ok
        response2.assertThat().statusCode(200).body("success", equalTo(true));

        //check the received response body
        assertThat("Order status is not done", extractedCreatedOrder.getOrder().getStatus(), equalTo("done"));
    }

    @Test
    @DisplayName("Create an order without authorization")
    public void createOrderWithoutAuthorization() {

        //create empty access token
        String accessTokenFromResponse = "";

        //We need to explicitly create a new order using recently prepared ingredients and an extracted access token
        response2 = postOrdersRequest.createNewOrder(ingredients, accessTokenFromResponse);

        //Assertions section
        // check if the response and 'success' messages are NOT ok
        response2.assertThat().statusCode(403).body("success", equalTo(false));
    }

    @Test
    @DisplayName("Create an order with authorization")
    public void createOrderWithIngredients() {
        //Assertions section
        // check if the response and 'success' messages are ok
        response2.assertThat().statusCode(200).body("success", equalTo(true));

        //check the received response body
        assertThat("Id numbers of the 1st ingredients are not equal ", extractedCreatedOrder.getOrder().getIngredients().get(0).get_id(),
                equalTo(newGeneratedData.getArrayWithActualIngredients().get(7)));
        assertThat("Id numbers of the 2nd ingredients are not equal", extractedCreatedOrder.getOrder().getIngredients().get(1).get_id(),
                equalTo(newGeneratedData.getArrayWithActualIngredients().get(2)));
        assertThat("Id numbers of the 3rd ingredients are not equal", extractedCreatedOrder.getOrder().getIngredients().get(2).get_id(),
                equalTo(newGeneratedData.getArrayWithActualIngredients().get(3)));
        assertThat("Order status is not done", extractedCreatedOrder.getOrder().getStatus(), equalTo("done"));
    }

    @Test
    @DisplayName("Create an order with wrong ingredient Id")
    public void createOrderWithWrongIngredients() {

        //set up a dummy ingredient hash
        ingredients.setIngredients(
                new String[]{dummyIngredientId1});

        //creating a new order using dummy ingredient and an extracted access token
        ValidatableResponse response2 = postOrdersRequest.createNewOrder(ingredients, accessTokenFromResponse);

        //Assertions section
        // check the status and wording in the response body
        response2.assertThat().statusCode(500);
        String extractedResponseBody = response2.extract().body().asString();
        assertThat(extractedResponseBody.contains("Internal Server Error"), equalTo(true));
    }

    @Test
    @DisplayName("Create an order with wrong ingredient Id")
    public void createOrderWithoutIngredients() {

        //set up empty ingredient
        wrongIngredients.setIngredients(
                new String[]{emptyIngredients});

        //creating a new order using dummy ingredient and an extracted access token
        ValidatableResponse response2 = postOrdersRequest.createNewOrderWithoutIngredients(accessTokenFromResponse);

        //Assertions section
        // check the status and body of the response
        response2.assertThat().statusCode(400).body("message",equalTo("Ingredient ids must be provided"));
    }
}