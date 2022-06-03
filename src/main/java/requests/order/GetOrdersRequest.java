package requests.order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import requests.GeneralData;
import static io.restassured.RestAssured.given;

public class GetOrdersRequest {
    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Request to get all orders")
    public ValidatableResponse getListOfOrders () {
        return given().
                 when().get(mainUrl + "orders/all").then();
    }

    @Step("Request to get an order of the specific user")
    public ValidatableResponse getOrder (String userAccessToken) {
        return given().header("Content-type", "application/json")
                .and().header("Authorization", userAccessToken)
                .when().get(mainUrl + "orders").then();
    }

}


