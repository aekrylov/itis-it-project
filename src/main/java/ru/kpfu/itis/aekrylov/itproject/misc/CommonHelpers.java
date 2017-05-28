package ru.kpfu.itis.aekrylov.itproject.misc;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.security.UserPrincipal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public static void saveImage(Path name, InputStream stream) throws IOException {
        Path absolutePath = imageDir.resolve(name);
        Files.createDirectories(absolutePath.getParent());
        Files.copy(stream, absolutePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void saveImage(Path name, MultipartFile image) throws IOException {
        saveImage(name, image.getInputStream());
    }

    public static File getImage(Path name) {
        return imageDir.resolve(name).toAbsolutePath().toFile();
    }

    public static String getErrorMessage(String errorCode) {
        return errors.get(errorCode);
    }

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof UserPrincipal))
            return null;
        return ((UserPrincipal) principal).getUser();
    }

}
