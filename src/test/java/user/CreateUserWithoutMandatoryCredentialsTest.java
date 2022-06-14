package user;
import data.GeneratedData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requests.user.PostUserRequest;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserWithoutMandatoryCredentialsTest {

    private final String email;
    private final String password;
    private final String name;

    public CreateUserWithoutMandatoryCredentialsTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Description("Preparation of 3 possible scenarios where each of three credentials is missing")
    @Parameterized.Parameters (name = "{0} - user name is missing, {1} - password is missing, {2} - email is missing")
    public static Object[][] getNewTestData() {
        return new Object[][] {
                {new GeneratedData().getUserEmail(), new GeneratedData().getUserPassword(), ""},
                {new GeneratedData().getUserEmail(), "", new GeneratedData().getUserName()},
                {"", new GeneratedData().getUserPassword(), new GeneratedData().getUserName()},
        };
    }

    @Test
    @DisplayName("Parameterized test whether a user can be created without at least one mandatory credential")
    public void testCreateUserWithoutOneCredential() {
        PostUserRequest newPostUserRequest = new PostUserRequest();
        ValidatableResponse response = newPostUserRequest.createNewUser(email, password, name);
        response.assertThat().body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }
}

