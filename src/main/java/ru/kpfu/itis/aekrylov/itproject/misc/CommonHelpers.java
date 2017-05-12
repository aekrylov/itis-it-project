package ru.kpfu.itis.aekrylov.itproject.misc;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.services.ChatService;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 8:14 PM
 * 11-501
 */
public class CommonHelpers {
    private static Map<String, String> errors = new HashMap<>();
    static {
        errors.put("password_match", "Passwords don't match");

        errors.put("password_invalid", "Invalid password");
        errors.put("username_404", "Username doesn't exist");
        errors.put("username_taken", "Username already taken");

        errors.put("db", "Database error");
    }

    private static Path imageDir = Paths.get("/media/d/www/tmp/img/").toAbsolutePath();
    private static ChatService chatService = ChatService.getInstance();
    private static UserService userService = UserService.getInstance();

    public static void saveImage(Path name, InputStream stream) throws IOException {
        Path absolutePath = imageDir.resolve(name);
        Files.createDirectories(absolutePath.getParent());
        Files.copy(stream, absolutePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static File getImage(Path name) {
        return imageDir.resolve(name).toAbsolutePath().toFile();
    }

    public static String getErrorMessage(String errorCode) {
        return errors.get(errorCode);
    }

    public static void render(ServletContext sc, HttpServletRequest request, HttpServletResponse response,
                              String templateName, Map<String, Object> dataModel)
            throws IOException {

        if(request.getSession().getAttribute("username") != null) {
            if(dataModel == null)
                dataModel = new HashMap<>();

            try {
                User user = getCurrentUser(request);
                dataModel.put("current_user", user);
                dataModel.put("unread_count", chatService.getUnreadCount(user));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        render(sc, response, templateName, dataModel);
    }

    private static void render(ServletContext sc, HttpServletResponse resp,
                              String templateName, Object dataModel) throws IOException {
        Template tmpl = ConfigSingleton.getConfig(sc).getTemplate(templateName);
        try {
            resp.setContentType("text/html;charset=UTF-8");
            tmpl.process(dataModel, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    public static User getCurrentUser(HttpServletRequest req) throws SQLException {
        try {
            return userService.get((String) req.getSession().getAttribute("username"));
        } catch (NotFoundException e) {
            return null;
        }
    }

    public static String encrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] digest =  md.digest();
            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException ignored) { return null; }
    }

}
