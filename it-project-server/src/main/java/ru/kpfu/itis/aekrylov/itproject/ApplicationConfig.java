package ru.kpfu.itis.aekrylov.itproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 12:04 PM
 */

@Configuration
@PropertySource(value = "classpath:db.properties")
@PropertySource(value = "classpath:application.properties")
@ComponentScan(basePackageClasses = {UserService.class})
@EnableJpaRepositories
@EnableTransactionManagement
public class ApplicationConfig {

    private final ConfigurableEnvironment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        //configurer.setProperties(allProperties);
        return configurer;
    }

    @Autowired
    public ApplicationConfig(ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public Properties allProperties() {
        Properties properties = new Properties();
        for(org.springframework.core.env.PropertySource<?> source: env.getPropertySources()) {
            if(source instanceof MapPropertySource) {
                properties.putAll(((MapPropertySource) source).getSource());
            }
        }

        return properties;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
