package requests.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import requests.GeneralData;

import static io.restassured.RestAssured.*;

public class GetUserRequest {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Request to get user's details")
    public ValidatableResponse getUserDetails(String usersAccessToken) {

        return given().header("Authorization", usersAccessToken)
                .when().get(mainUrl + "auth/user").then();
    }

}