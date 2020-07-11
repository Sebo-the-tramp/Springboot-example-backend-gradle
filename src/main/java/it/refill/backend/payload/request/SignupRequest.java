package it.refill.backend.payload.request;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    @Email
    private String username;

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;


    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
    
    @NotBlank
    private String telephone;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getTelephone() {
        return this.telephone;
    }

}