/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:05 PM
 * 11-501
 * Task
 */
package app.models;

import app.misc.ValidationException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:05 PM
 * 11-501
 * Task 
 */
public class User extends Entity {
    private int id;
    private String username;
    private String password;

    @OwnField
    private String password_raw;

    private String name;
    private String email;
    private String photo;

    private double rating;

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

    public User() {}

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password_raw = password;
        this.password = app.Helpers.encrypt(password);
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
