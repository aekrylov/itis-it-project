package ru.kpfu.itis.aekrylov.itproject.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 5:48 PM
 */
public class PostListItemView implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Hyperlink header;

    @FXML
    private Label price;

    @FXML
    private AnchorPane root;

    private Post post;
    private boolean initialized = false;

    public PostListItemView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/postList-item.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public AnchorPane getRoot() {
        return root;
    }

    public void setPost(Post post) {
        this.post = post;
        if(initialized)
            processPost();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialized = true;

        header.setOnAction(event -> StaticHelpers.openItemPage((Integer) header.getUserData()));
        if(post != null) {
            processPost();
        }
    }

    private void processPost() {
        header.setText(post.getProduct().getName());
        header.setUserData(post.getId());
        price.setText(String.valueOf(post.getProduct().getPrice()));

        String photoUrl = post.getProduct().getPhoto();
        if(photoUrl != null)
            image.setImage(new Image(photoUrl));
    }
}
