package app;

import app.models.Messages;
import app.models.User;
import app.models.Users;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
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
public class Helpers {
    private static Map<String, String> errors = new HashMap<>();
    static {
        errors.put("password_match", "Passwords don't match");

        errors.put("password_invalid", "Invalid password");
        errors.put("username_404", "Username doesn't exist");
        errors.put("username_taken", "Username already taken");

        errors.put("db", "Database error");
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
                User user = Users.get((String) request.getSession().getAttribute("username"));
                dataModel.put("current_user", user);
                dataModel.put("unread_count", Messages.getUnreadCount(user));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        render(sc, response, templateName, dataModel);
    }

    public static void render(ServletContext sc, HttpServletResponse resp,
                              String templateName, Object dataModel) throws IOException {
        Template tmpl = ConfigSingleton.getConfig(sc).getTemplate(templateName);
        try {
            resp.setContentType("text/html;charset=UTF-8");
            tmpl.process(dataModel, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    //TODO migrate to basic servlet?
    public static String getUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("username");
    }

    public static User getCurrentUser(HttpServletRequest req) throws SQLException {
        return Users.get((String) req.getSession().getAttribute("username"));
    }

    public static String encrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] digest =  md.digest();
            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
