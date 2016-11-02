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

    public User(int id, String username, String password, String name, String email, String photo, double rating) {
        this(username, password, name, email);
        this.photo = photo;
        this.rating = rating;
        this.id = id;
    }

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean validate() throws ValidationException {
        if(!username.matches("^[a-zA-Z0-9]{3,}$"))
            throw new ValidationException("invalid", "username");

        if(!password.matches("^[a-zA-Z0-9]{6,}$"))
            throw new ValidationException("invalid", "password");

        if(name == null)
            throw new ValidationException("empty", "name");

        return true;
    }

}
