/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:05 PM
 * 11-501
 * Task
 */
package app.models;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:05 PM
 * 11-501
 * Task 
 */
public class User {
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

    public User(String username, String password, String name, String email, int id, String photo, double rating) {
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

}
