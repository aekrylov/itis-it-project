package ru.kpfu.itis.aekrylov.itproject.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 8:04 PM
 */

@Component
public class HelperBean {

    @Value("${app.url.item}")
    private String itemUrl;

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
