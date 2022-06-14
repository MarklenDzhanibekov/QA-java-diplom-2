package model.user;

public class UserCredentialsModelBuilder {
    private String email;
    private String password;
    private String name;

    public UserCredentialsModelBuilder() {}

    public UserCredentialsModelBuilder(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        private UserCredentialsModelBuilder userCredentialsModelBuilder;

        public Builder() {
            userCredentialsModelBuilder = new UserCredentialsModelBuilder();
        }

        public Builder withEmail(String email) {
            userCredentialsModelBuilder.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            userCredentialsModelBuilder.password = password;
            return this;
        }

        public Builder withName(String name) {
            userCredentialsModelBuilder.name = name;
            return this;
        }

        public UserCredentialsModelBuilder build ()
        {return userCredentialsModelBuilder;}

    }

}
