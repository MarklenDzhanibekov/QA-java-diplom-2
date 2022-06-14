package user;
import data.GeneratedData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.user.UserPojoModel;
import model.user.UserCredentialsModelBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.user.DeleteUserRequest;
import requests.user.PatchUserRequest;
import requests.user.PostUserRequest;
import steps.StepToDeleteUser;
import steps.StepToGetTokens;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserCredentialsTest {
    GeneratedData newGeneratedData = new GeneratedData();
    String password = newGeneratedData.getUserPassword();
    String email = newGeneratedData.getUserEmail();
    String name = newGeneratedData.getUserName();
    String newPassword = newGeneratedData.getUserPassword();
    String newEmail = newGeneratedData.getUserEmail();
    String newName = newGeneratedData.getUserName();
    UserPojoModel userPojoModel = new UserPojoModel();
    String accessTokenFromResponse;
    PostUserRequest newPostUserRequest = new PostUserRequest();
    StepToGetTokens newStepToGetTokens = new StepToGetTokens();
    ValidatableResponse response;

    @After
    @DisplayName("delete all users that were created for tests purpose within current test")
    public void deleteAllUsersCreatedInLoginTestClass() {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.deleteUser(accessTokenFromResponse);
    }

    @Before
    @DisplayName("getting preparations ready for the upcoming tests")
    public void executedBeforeEach() {
        //Creating a new user
        response = newPostUserRequest.createNewUserWB(new UserCredentialsModelBuilder.Builder()
                        .withName(name)
                        .withEmail(email)
                        .withPassword(password)
                        .build()).assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    //Check whether name, email and password fields can be updated.
    @Test
    @DisplayName("User's email can be updated With Authorization")
    public void emailCanBeUpdatedWithAuthorizationTest() {

        //Extracting a user's access token
       accessTokenFromResponse = newStepToGetTokens.getAccessToken(response);

        //Executing a PATCH request and verify that status 200 is coming back, and success has a "True" status
        PatchUserRequest newPatchUserRequest = new PatchUserRequest();
        ValidatableResponse response1 = newPatchUserRequest.updateUser(accessTokenFromResponse,
                        new UserCredentialsModelBuilder.Builder()
                                .withEmail(newEmail)
                                .build())
                .body("success", equalTo(true))
                .statusCode(200);

        // Check if the email was updated.
        // Executing login under the updated user's credentials and check if the email was updated
        PostUserRequest postUserRequest = new PostUserRequest();
        ValidatableResponse response3 = postUserRequest.login(newEmail, password);
        response3.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    //Check whether name, email and password fields can be updated.
    @Test
    @DisplayName("update a password without authorization")
    public void passwordCanBeUpdatedWithAuthorizationTest() {

        //Extracting a user's access token
        accessTokenFromResponse = newStepToGetTokens.getAccessToken(response);

        //Executing a PATCH request and verify that status 200 is coming back, and success has a "True" status
        PatchUserRequest newPatchUserRequest = new PatchUserRequest();
        ValidatableResponse response1 = newPatchUserRequest.updateUser(accessTokenFromResponse,
                        new UserCredentialsModelBuilder.Builder()
                                .withPassword(newPassword)
                                .build())
                .body("success", equalTo(true))
                .statusCode(200);

        // Executing login under the updated user's credentials and check if the email was updated
        PostUserRequest postUserRequest = new PostUserRequest();
        ValidatableResponse response3 = postUserRequest.login(email, newPassword);
        response3.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("update a name with authorization")
    public void nameCanBeUpdatedWithAuthorizationTest() {

        //Extracting a user's access token
        accessTokenFromResponse = newStepToGetTokens.getAccessToken(response);

        //Executing a PATCH request and verify that status 200 is coming back, and success has a "True" status
        PatchUserRequest newPatchUserRequest = new PatchUserRequest();
        ValidatableResponse response1 = newPatchUserRequest.updateUser(accessTokenFromResponse,
                        new UserCredentialsModelBuilder.Builder()
                                .withName(newName)
                                .build())
                .body("success", equalTo(true))
                .statusCode(200);

        // Check if the name was updated.
        // The way to check as follows:
        // by passing an updated email for the user we check if a returned body contains the updated user's name.
        userPojoModel = newPatchUserRequest.updateUserAndExtractUserDetails(accessTokenFromResponse, new UserCredentialsModelBuilder.Builder()
                .withEmail(newEmail)
                .build());
        assertThat(userPojoModel.getUser().getUsername(), equalTo(newName));
    }

    @Test
    @DisplayName("update a password without authorization")
    public void updatePasswordWithOutAuthorizationTest() {

        //Extracting a user's access token
        accessTokenFromResponse = "";

        //Executing a PATCH request
        PatchUserRequest newPatchUserRequest = new PatchUserRequest();
        ValidatableResponse response1 = newPatchUserRequest.updateUser(accessTokenFromResponse,
                        new UserCredentialsModelBuilder.Builder()
                                .withPassword(newPassword)
                                .build())
                .body("success", equalTo(false)).and()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }

    @Test
    @DisplayName("update an email without authorization")
    public void updateEmailWithOutAuthorizationTest() {

        //initializing empty access token
       accessTokenFromResponse = "";

        //Executing a PATCH request and verify that status 200 is coming back, and success has a "True" status
        PatchUserRequest newPatchUserRequest = new PatchUserRequest();
        ValidatableResponse response1 = newPatchUserRequest.updateUser(accessTokenFromResponse,
                        new UserCredentialsModelBuilder.Builder()
                                .withEmail(newEmail)
                                .build())
                .body("success", equalTo(false)).and()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }

    @Test
    @DisplayName("update name without authorization")
    public void updateNameWithOutAuthorizationTest() {

        //initializing empty access token
        accessTokenFromResponse = "";

        //Executing a PATCH request and verify that status 200 is coming back, and success has a "True" status
        PatchUserRequest newPatchUserRequest = new PatchUserRequest();
        ValidatableResponse response1 = newPatchUserRequest.updateUser(accessTokenFromResponse,
                        new UserCredentialsModelBuilder.Builder()
                                .withName(newName)
                                .build())
                .body("success", equalTo(false)).and()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }
}
