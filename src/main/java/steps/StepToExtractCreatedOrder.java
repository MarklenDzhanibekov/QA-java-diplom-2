package steps;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.orders.CreatedOrder;
import requests.GeneralData;

public class StepToExtractCreatedOrder {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Step to extract a body of a created order")
    public CreatedOrder extractCreatedOrder (ValidatableResponse response) {

        CreatedOrder extractCreatedOrder = response
                .extract()
                .body()
                .as(CreatedOrder.class);

        return  extractCreatedOrder;
    }
}
