package ru.kpfu.itis.aekrylov.itproject.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 6:07 PM
 */
public class RegistrationForm {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$")
    private String password;
    private String password_repeat;

    @NotEmpty
    private String name;

    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_repeat() {
        return password_repeat;
    }

    public void setPassword_repeat(String password_repeat) {
        this.password_repeat = password_repeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
