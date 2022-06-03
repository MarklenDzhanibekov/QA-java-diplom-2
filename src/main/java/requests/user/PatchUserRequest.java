package requests.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.user.UserPojoModel;
import model.user.UserCredentialsModelBuilder;
import requests.GeneralData;

import static io.restassured.RestAssured.*;

public class PatchUserRequest {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step ("Request to update user's details")
    public ValidatableResponse updateUser(String usersAccessToken, UserCredentialsModelBuilder userCredentialsModelBuilder) {

        return given().header("Authorization", usersAccessToken)
                .header("Content-type", "application/json")
                .body(userCredentialsModelBuilder)
                .when().patch(mainUrl + "auth/user").then();
    }

    @Step ("Request to update user's details")
    public UserPojoModel updateUserAndExtractUserDetails(String usersAccessToken, UserCredentialsModelBuilder userCredentialsModelBuilder) {

        UserPojoModel extractedUser = given()
                .header("Content-type", "application/json")
                .header("Authorization", usersAccessToken)
                .body(userCredentialsModelBuilder)
                .patch(mainUrl + "auth/user")
                .body().as(UserPojoModel.class);
        return extractedUser;
    }

}

