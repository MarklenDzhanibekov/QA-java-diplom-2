package  steps;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.user.UserPojoModel;
import requests.GeneralData;

public class StepToExtractUserDetails {

        String mainUrl = new GeneralData().getMAIN_URL();

        @Step("Step to extract body from a received GET request and convert it to object")
        public UserPojoModel extractUserDetails(ValidatableResponse response) {
                UserPojoModel extractedUserDetails = response
                        .extract()
                        .body().as(UserPojoModel.class);
                return extractedUserDetails;
        }
}