package app.servlets.admin;

import app.models.*;

import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:20 PM
 */
public class Helpers {

    private static Map<String, DAO> daoMap = new HashMap<>();
    private static Map<String, String> tableTitles = new HashMap<>();

    static {
        daoMap.put("users", new Users());
        daoMap.put("products", new Products());
        //daoMap.put("messages", new Messages());
        daoMap.put("feedbacks", new Feedbacks());
        daoMap.put("posts", new Posts());

        tableTitles.put("users", "Пользователи");
        tableTitles.put("products", "Товары");
        //tableTitles.put("messages", "Сообщения");
        tableTitles.put("feedbacks", "Отзывы");
        tableTitles.put("posts", "Публикации");
    }

    public static Map<String, String> getTableTitles() {
        return tableTitles;
    }

    public static String getTableTitle(String tableName) {
        return tableTitles.get(tableName);
    }

    public static DAO getDao(String table) {
        return daoMap.get(table);
    }

}
