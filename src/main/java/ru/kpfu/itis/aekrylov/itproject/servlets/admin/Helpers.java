package ru.kpfu.itis.aekrylov.itproject.servlets.admin;

import ru.kpfu.itis.aekrylov.itproject.entities.*;
import ru.kpfu.itis.aekrylov.itproject.models.*;

import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:20 PM
 */
public class Helpers {

    private static Map<String, DAO<?>> daoMap = new HashMap<>();
    private static Map<String, String> tableTitles = new HashMap<>();

    static {
        daoMap.put("users", new DAO<>(User.class));
        daoMap.put("products", new DAO<>(Product.class));
        daoMap.put("messages", new DAO<>(Message.class));
        daoMap.put("feedbacks", new DAO<>(Feedback.class));
        daoMap.put("posts", new DAO<>(Post.class));
        daoMap.put("buy_sells", new DAO<>(BuySell.class));


        tableTitles.put("users", "Пользователи");
        tableTitles.put("products", "Товары");
        tableTitles.put("messages", "Сообщения");
        tableTitles.put("feedbacks", "Отзывы");
        tableTitles.put("posts", "Публикации");
        tableTitles.put("buy_sells", "Купля-продажа");
    }

    public static Map<String, String> getTableTitles() {
        return tableTitles;
    }

    public static DAO<?> getDao(String table) {
        return daoMap.get(table);
    }

}
