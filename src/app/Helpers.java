package app;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static void render(ServletContext sc, HttpServletResponse response, String templateName, Object dataModel)
            throws IOException {

        Template tmpl = ConfigSingleton.getConfig(sc).getTemplate(templateName);
        try {
            response.setContentType("text/html;charset=UTF-8");
            tmpl.process(dataModel, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
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
