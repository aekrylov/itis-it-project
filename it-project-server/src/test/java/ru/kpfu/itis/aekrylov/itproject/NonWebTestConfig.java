package ru.kpfu.itis.aekrylov.itproject;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import javax.sql.DataSource;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/31/17 9:45 AM
 *
 * Test configuration that excludes unneeded web facilities to speed up testing
 */

@SpringBootConfiguration
@Import(ApplicationConfig.class)
@ImportAutoConfiguration({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class NonWebTestConfig {

}
