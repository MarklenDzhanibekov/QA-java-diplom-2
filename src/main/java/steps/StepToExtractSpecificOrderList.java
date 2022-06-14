package steps;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.orders.OrdersListModel;
import requests.GeneralData;

public class StepToExtractSpecificOrderList {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Step to extract a body of an exact order list")
    public OrdersListModel extractOrderListBody (String usersAccessToken, ValidatableResponse response) {

        OrdersListModel extractedOrderListBody = response
                .header("Content-type", "application/json")
                .header("Authorization", usersAccessToken)
                .extract()
                .body()
                .as(OrdersListModel.class);

        return  extractedOrderListBody;
    }
}
