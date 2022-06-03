package requests.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.user.UserCredentialsModel;
import model.user.UserCredentialsModelBuilder;
import requests.GeneralData;

import static io.restassured.RestAssured.*;


public class PostUserRequest {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step ("Request to create a new user with specifying all needed credentials")
    public ValidatableResponse createNewUser(String email, String password, String name) {
            UserCredentialsModel newUserCredentialsModel = new UserCredentialsModel(email, password, name);

            return given().header("Content-type", "application/json")
                    .and().body(newUserCredentialsModel)
                    .when().post(mainUrl + "auth/register").then();
    }

    @Step("Request for login")
    public ValidatableResponse login(String email, String password) {
        UserCredentialsModel newUserCredentialsModel = new UserCredentialsModel(email, password);

        return given().header("Content-type", "application/json")
                .and().body(newUserCredentialsModel)
                .when().post(mainUrl + "auth/login").then();
    }

    @Step ("Request to create a new user with specifying all needed credentials")
    public ValidatableResponse createNewUserWB (UserCredentialsModelBuilder userCredentialsModelBuilder) {

        return given().header("Content-type", "application/json")
                .and().body(userCredentialsModelBuilder)
                .when().post(mainUrl + "auth/register").then();
    }


}
