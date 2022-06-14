package steps;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class StepToGetTokens {

    @Step("Step to get access token")
    public String getAccessToken(ValidatableResponse response) {
       String accessToken = response.extract().path("accessToken").toString();
       return accessToken;
    }

    @Step("Step to get refresh token")
    public String getRefreshToken(ValidatableResponse response) {
        String refreshToken = response.extract().path("refreshToken").toString();
        return refreshToken;
    }
}
