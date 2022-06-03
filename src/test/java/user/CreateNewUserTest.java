package user;
import data.GeneratedData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import requests.user.PostUserRequest;
import steps.StepToDeleteUser;
import steps.StepToGetTokens;
import static org.hamcrest.Matchers.equalTo;

public class CreateNewUserTest {
    GeneratedData newGeneratedData = new GeneratedData();
    String password = newGeneratedData.getUserPassword();
    String email = newGeneratedData.getUserEmail();
    String name = newGeneratedData.getUserName();
    StepToGetTokens stepToGetTokens = new StepToGetTokens();
    String accessTokenFromResponse;

    @After
    @DisplayName("delete all users that were created for tests purpose within current test")
    public void deleteAllUsersCreatedInLoginTestClass() {
        StepToDeleteUser stepToDeleteUser = new StepToDeleteUser();
        stepToDeleteUser.deletionUser(accessTokenFromResponse);
    };

    //Create a unique user
    @Test
    @DisplayName("Creating a new user")
    public void testUserCanBeCreated() {
        PostUserRequest newPostUserRequest = new PostUserRequest();
        ValidatableResponse response = newPostUserRequest.createNewUser(email, password, name);
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
        accessTokenFromResponse = stepToGetTokens.getAccessToken(response);
    }

    //Attempt to create a user that is already created
    @Test
    @DisplayName("Create a user that is already created")
    public void testTheSameUserCannotBeCreatedTwice() {
        PostUserRequest newPostUserRequest = new PostUserRequest();
        ValidatableResponse response = newPostUserRequest.createNewUser(email, password, name);
        ValidatableResponse response2 = newPostUserRequest.createNewUser(email, password, name);
        response2.assertThat().body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .and().statusCode(403);
        accessTokenFromResponse = stepToGetTokens.getAccessToken(response);
        }

}
