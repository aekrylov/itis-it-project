package ru.kpfu.itis.aekrylov.itproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resources;


/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 12:09 PM
 */

@Configuration
@ComponentScan(basePackageClasses = {ru.kpfu.itis.aekrylov.itproject.servlets.BaseServlet.class})
public class DispatcherConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/templates");

        return configurer;
    }
}
