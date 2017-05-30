package ru.kpfu.itis.aekrylov.itproject.client;

import javafx.scene.control.ListCell;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 5:46 PM
 */
public class PostCell extends ListCell<Post> {

    @Override
    protected void updateItem(Post item, boolean empty) {
        super.updateItem(item, empty);

        if(item != null) {
            PostListItemView itemView = new PostListItemView(item);
            //getChildren().add(itemView.getRoot());
            setGraphic(itemView.getRoot());
        } else {
            setGraphic(null);
        }
    }
}
