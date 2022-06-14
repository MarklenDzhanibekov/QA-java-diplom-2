package model.user;

public class UserCredentialsModel {
    private String email;
    private String password;
    private String name;

    public UserCredentialsModel() {}

    public UserCredentialsModel(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserCredentialsModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredentialsModel(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }
}
