package ro.chris.schlechta.request;

import javax.validation.constraints.NotNull;

public class RegisterRequestModel {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String passwordCheck;

    public String getFirstName() {
        return firstName;
    }

    public RegisterRequestModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterRequestModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterRequestModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequestModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public RegisterRequestModel setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
        return this;
    }
}