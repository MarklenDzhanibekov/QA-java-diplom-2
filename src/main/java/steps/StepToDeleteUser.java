package steps;
import requests.user.DeleteUserRequest;

public class StepToDeleteUser {
    public void deletionUser(String usersAccessToken) {

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        try {
            deleteUserRequest.deleteUser(usersAccessToken);
            throw new IllegalArgumentException();

        } catch (IllegalArgumentException exception) {
            System.out.println("usersAccessToken is invalid, so no users were created hence nothing to delete.");
        }
    }
}
