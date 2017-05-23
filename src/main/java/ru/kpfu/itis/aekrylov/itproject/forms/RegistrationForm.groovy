package ru.kpfu.itis.aekrylov.itproject.forms

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.Pattern

class RegistrationForm {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}\$")
    String username

    @Pattern(regexp = "^[a-zA-Z0-9]{6,}\$")
    String password
    String password_repeat

    @NotEmpty
    String name

    @Email
    String email

}