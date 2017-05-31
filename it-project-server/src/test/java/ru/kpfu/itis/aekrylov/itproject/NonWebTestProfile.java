package ru.kpfu.itis.aekrylov.itproject;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/31/17 10:29 AM
 *
 * Annotation for test classes that don't involve any of web capabilities
 */

@ActiveProfiles("test")
@SpringBootTest(classes = {NonWebTestConfig.class})
@Import(NonWebTestConfig.class)

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonWebTestProfile {

}
