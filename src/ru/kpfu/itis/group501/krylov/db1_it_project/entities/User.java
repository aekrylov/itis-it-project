/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:05 PM
 * 11-501
 * Task
 */
package ru.kpfu.itis.group501.krylov.db1_it_project.entities;

import ru.kpfu.itis.group501.krylov.db1_it_project.Helpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ValidationException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:05 PM
 * 11-501
 * Task 
 */
public class User extends Entity {
    int id;
    String username;
    String password;

    @OwnField
    private String password_raw;

    String name;
    String email;
    String photo;

    double rating;
    int rate_count = 0;

    String role = "user";

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoto() {
        return photo;
    }

    public double getRating() {
        return rating;
    }
    public int getRate_count() {
        return rate_count;
    }
    public String getRole() {
        return role;
    }

    public void addRating(int score) {
        rating = (rating*rate_count + score) / ++rate_count;
    }

    public User() {}

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password_raw = password;
        this.password = Helpers.encrypt(password);
        this.name = name;
        this.email = email;
    }

    public boolean validate() throws ValidationException {
        if(!username.matches("^[a-zA-Z0-9]{3,}$"))
            throw new ValidationException("invalid", "username");

        if(!password_raw.matches("^[a-zA-Z0-9]{6,}$"))
            throw new ValidationException("invalid", "password");

        if(name == null || name.equals(""))
            throw new ValidationException("empty", "name");

        if(email == null || !email.matches("[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+")) {
            throw new ValidationException("invalid", "email");
        }

        return true;
    }

}
