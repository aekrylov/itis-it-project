package ru.kpfu.itis.aekrylov.itproject.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;

import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 5:48 PM
 */
public class PostListItemView {

    @FXML
    private ImageView imageView;

    @FXML
    private Hyperlink header;

    @FXML
    private Label price;

    @FXML
    private AnchorPane root;

    public PostListItemView(Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/postList-item.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        header.setText(post.getProduct().getName());
        price.setText(String.valueOf(post.getProduct().getPrice()));
        //imageView.setImage(new Image(post.getProduct().getPhoto()));
    }

    public AnchorPane getRoot() {
        return root;
    }
}
