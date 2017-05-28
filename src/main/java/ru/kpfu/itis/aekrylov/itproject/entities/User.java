package ru.kpfu.itis.aekrylov.itproject.entities;

import ru.kpfu.itis.aekrylov.itproject.misc.ValidationException;

import javax.persistence.*;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:05 PM
 * 11-501
 * Task 
 */
@javax.persistence.Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    int id;
    String username;
    String password;

    @Transient
    private String password_raw;

    String name;
    String email;
    String photo;

    double rating;
    int rate_count = 0;

    String role = "USER";

    boolean has_avatar = false;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Post> posts;

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
    public boolean isHas_avatar() {
        return has_avatar;
    }

    public void addRating(int score) {
        setRate_count(getRate_count() + 1);
        setRating((rating*rate_count + score) / getRate_count());
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setHas_avatar(boolean has_avatar) {
        this.has_avatar = has_avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword_raw() {
        return password_raw;
    }

    public void setPassword_raw(String password_raw) {
        this.password_raw = password_raw;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setRate_count(int rate_count) {
        this.rate_count = rate_count;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User() {}

    //todo validation
    public void validate(boolean checkPassword) throws ValidationException {
        if(!username.matches("^[a-zA-Z0-9]{3,}$"))
            throw new ValidationException("invalid", "username");

        if(checkPassword && !password_raw.matches("^[a-zA-Z0-9]{6,}$"))
            throw new ValidationException("invalid", "password");

        if(name == null || name.equals(""))
            throw new ValidationException("empty", "name");

        if(email == null || !email.matches("[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+")) {
            throw new ValidationException("invalid", "email");
        }

    }

}
