package requests.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import requests.GeneralData;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class DeleteUserRequest {
    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Request to delete a user using its access token")
    public ValidatableResponse deleteUser(String usersAccessToken) {

        return given().header("Authorization", usersAccessToken)
                .when().delete(mainUrl + "auth/user").then();
                /*.assertThat().statusCode(202)
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));*/
    }
}
