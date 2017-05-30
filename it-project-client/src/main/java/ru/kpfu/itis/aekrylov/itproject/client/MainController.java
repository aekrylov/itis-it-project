package ru.kpfu.itis.aekrylov.itproject.client;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.forms.FilterForm;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers.isEmpty;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 4:34 PM
 */

@FXMLController
public class MainController implements Initializable {
    public WebView webView;
    public TextField tfBrand;
    public TextField tfModel;

    public TextField tfPriceLow;
    public TextField tfPriceHigh;

    public Button btnSubmit;
    public ListView<Post> postList;

    private RestTemplate restTemplate = new RestTemplate();

    private final ObservableList<Post> observablePosts = FXCollections.observableArrayList();

    @Value("${app.post_base}")
    private String postBase;

    public void submitForm() {
        FilterForm form = new FilterForm();
        if(!isEmpty(tfBrand.getText()))
            form.setBrand(tfBrand.getText());

        if(!isEmpty(tfModel.getText()))
            form.setModel(tfModel.getText());
        if(!isEmpty(tfPriceLow.getText()))
            form.setPrice_low(Integer.valueOf(tfPriceLow.getText()));

        if(!isEmpty(tfPriceHigh.getText()))
            form.setPrice_high(Integer.valueOf(tfPriceHigh.getText()));

        ResponseEntity<Post[]> resp = restTemplate.postForEntity(URI.create(postBase).resolve("./items"), form, Post[].class);
        List<Post> posts = Arrays.stream(resp.getBody()).collect(Collectors.toList());
        observablePosts.setAll(posts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postList.setItems(observablePosts);
        postList.setCellFactory(view -> new PostCell());
    }
}
