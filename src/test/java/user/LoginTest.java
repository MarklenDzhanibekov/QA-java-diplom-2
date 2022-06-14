package user;
import data.GeneratedData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import requests.user.DeleteUserRequest;
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
    ValidatableResponse responseCreateNewUser, response2;
    PostUserRequest newPostUserRequest = new PostUserRequest();
    ValidatableResponse responseLoginUnderCreatedUser;

    @Before
    @DisplayName("getting preparations ready for the upcoming tests")
    public void executedBeforeEach() {
        PostUserRequest newPostUserRequest = new PostUserRequest();
        responseCreateNewUser = newPostUserRequest.createNewUser(email, password, name);
    }

    @After
    @DisplayName("delete all users that were created for tests purpose within current test")
    public void deleteAllUsersCreatedInLoginTestClass() {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.deleteUser(accessTokenFromResponse);
    }

    @Test
    @DisplayName("Login under an existing user")
    public void authorizationUnderExistingUser() {
        responseLoginUnderCreatedUser = newPostUserRequest.login(email, password);
        responseLoginUnderCreatedUser.assertThat().body("success", equalTo(true)).and().statusCode(200);
        accessTokenFromResponse = newStepToGetTokens.getAccessToken(responseCreateNewUser);
    }

    @Test
    @DisplayName("Login using an incorrect email")
    public void loginUsingIncorrectEmail() {
        responseLoginUnderCreatedUser = newPostUserRequest.login(incorrectEmail, password);
        responseLoginUnderCreatedUser.assertThat().body("success", equalTo(false)).and().statusCode(401);
        accessTokenFromResponse = newStepToGetTokens.getAccessToken(responseCreateNewUser);
    }

    @Test
    @DisplayName("Login using an incorrect password")
    public void loginUsingIncorrectPassword() {
        responseLoginUnderCreatedUser = newPostUserRequest.login(email, incorrectPassword);
        responseLoginUnderCreatedUser.assertThat().body("success", equalTo(false)).and().statusCode(401);
        accessTokenFromResponse = newStepToGetTokens.getAccessToken(responseCreateNewUser);
    }
}
