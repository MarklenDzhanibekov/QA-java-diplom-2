package steps;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.orders.OrdersListModel;
import requests.GeneralData;

public class StepToExtractAllOrdersList {

    String mainUrl = new GeneralData().getMAIN_URL();

    @Step("Step to extract a body of an order list")
    public OrdersListModel extractOrderListBody (ValidatableResponse response) {

        OrdersListModel extractedOrderListBody = response
                .extract()
                .body()
                .as(OrdersListModel.class);

        return  extractedOrderListBody;
    }
}
