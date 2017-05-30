package ru.kpfu.itis.aekrylov.itproject.misc;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 7:01 PM
 */

@Component
public class HelperBean {

    private final Cloudinary cloudinary;

    public HelperBean(@Value("${cloudinary.url}") String cloudinaryUrl) {
        cloudinary = new Cloudinary(cloudinaryUrl);
    }

    public String uploadImage(MultipartFile image) {
        try {
            Map result = cloudinary.uploader().upload(image.getBytes(), new HashMap());
            return (String) result.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
