package ru.kpfu.itis.aekrylov.itproject;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.test.context.ActiveProfiles;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/31/17 2:44 PM
 *
 * Test configuration that doesn't scan anything
 */

@SpringBootConfiguration
@ActiveProfiles("test")
public @interface NoContextTestProfile {
}
