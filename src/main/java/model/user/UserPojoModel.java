package model.user;

public class UserPojoModel {
    private UserCredentialsModel user;
    private String success;


    public UserCredentialsModel getUser() {
        return user;
    }

    public void setUser(UserCredentialsModel user) {
        this.user = user;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
