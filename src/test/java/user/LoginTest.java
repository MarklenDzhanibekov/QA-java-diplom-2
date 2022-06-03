package user;
import data.GeneratedData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import requests.user.PostUserRequest;
import steps.StepToDeleteUser;
import steps.StepToGetTokens;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    StepToGetTokens newStepToGetTokens = new StepToGetTokens();
    GeneratedData newGeneratedData = new GeneratedData();
    String password = newGeneratedData.getUserPassword();
    String email = newGeneratedData.getUserEmail();
    String incorrectPassword = newGeneratedData.getUserPassword();
    String incorrectEmail = newGeneratedData.getUserEmail();
    String name = newGeneratedData.getUserName();
    String accessTokenFromResponse;

    @After
    @DisplayName("delete all users that were created for tests purpose within current test")
    public void deleteAllUsersCreatedInLoginTestClass() {
        StepToDeleteUser stepToDeleteUser = new StepToDeleteUser();
        stepToDeleteUser.deletionUser(accessTokenFromResponse);
    };

    @Test
    @DisplayName("Login under an existing user")
    public void authorizationUnderExistingUser() {
        PostUserRequest newPostUserRequest = new PostUserRequest();
        ValidatableResponse responseCreateNewUser = newPostUserRequest.createNewUser(email, password, name);

        ValidatableResponse responseLoginUnderCreatedUser = newPostUserRequest.login(email, password);
        responseLoginUnderCreatedUser.assertThat().body("success", equalTo(true)).and().statusCode(200);

        accessTokenFromResponse = newStepToGetTokens.getAccessToken(responseCreateNewUser);
    }

    @Test
    @DisplayName("Login using an incorrect email")
    public void loginUsingIncorrectEmail() {
        PostUserRequest newPostUserRequest = new PostUserRequest();
        ValidatableResponse responseCreateNewUser = newPostUserRequest.createNewUser(email, password, name);

        ValidatableResponse responseLoginUnderCreatedUser = newPostUserRequest.login(incorrectEmail, password);
        responseLoginUnderCreatedUser.assertThat().body("success", equalTo(false)).and().statusCode(401);

        accessTokenFromResponse = newStepToGetTokens.getAccessToken(responseCreateNewUser);
    }

    @Test
    @DisplayName("Login using an incorrect password")
    public void loginUsingIncorrectPassword() {
        PostUserRequest newPostUserRequest = new PostUserRequest();
        ValidatableResponse responseCreateNewUser = newPostUserRequest.createNewUser(email, password, name);

        ValidatableResponse responseLoginUnderCreatedUser = newPostUserRequest.login(email, incorrectPassword);
        responseLoginUnderCreatedUser.assertThat().body("success", equalTo(false)).and().statusCode(401);

        accessTokenFromResponse = newStepToGetTokens.getAccessToken(responseCreateNewUser);
    }
}
