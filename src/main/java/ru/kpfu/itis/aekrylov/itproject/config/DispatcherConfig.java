package ru.kpfu.itis.aekrylov.itproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;


/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 12:09 PM
 */

@Configuration
@ComponentScan(basePackageClasses = {ru.kpfu.itis.aekrylov.itproject.controllers.MainController.class})
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@EnableWebSecurity
public class DispatcherConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/templates");

        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver("", ".ftl");
        resolver.setOrder(1);
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }
}
