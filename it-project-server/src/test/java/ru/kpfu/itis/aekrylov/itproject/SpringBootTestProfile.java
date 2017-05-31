package ru.kpfu.itis.aekrylov.itproject;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/31/17 10:29 AM
 */

@ActiveProfiles("test")
@SpringBootTest
@Import(TestConfig.class)

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringBootTestProfile {

}
