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
    private String pass;

    @NotNull
    private String passCheck;

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


    public String getPass() {
        return pass;
    }

    public RegisterRequestModel setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public String getPassCheck() {
        return passCheck;
    }

    public RegisterRequestModel setPassCheck(String passCheck) {
        this.passCheck = passCheck;
        return this;
    }
}