package ru.kpfu.itis.aekrylov.itproject.client;

import javafx.application.HostServices;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 8:14 PM
 */
public class StaticHelpers {

    public static HostServices hostServices;

    public static String itemUrl;

    public static void openUrl(String url) {
        hostServices.showDocument(url);
    }

    public static void openItemPage(int id) {
        openUrl(itemUrl + id);
    }
}
