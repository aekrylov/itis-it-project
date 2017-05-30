package ru.kpfu.itis.aekrylov.itproject.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Hyperlink;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 7:56 PM
 *
 * Hyperlink with URL property
 */
public class UrlHyperlink extends Hyperlink {

    private StringProperty urlProperty = new SimpleStringProperty();

    public UrlHyperlink() {
    }

    public UrlHyperlink(String text, String url) {
        super(text);
        urlProperty.setValue(url);
    }

    public String getUrl() {
        return urlProperty.getValue();
    }

    public void setUrl(String url) {
        urlProperty.setValue(url);
    }

    public StringProperty urlProperty() {
        return urlProperty;
    }
}
