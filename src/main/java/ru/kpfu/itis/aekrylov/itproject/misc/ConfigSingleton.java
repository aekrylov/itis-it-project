package ru.kpfu.itis.aekrylov.itproject.misc;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 07.10.2015 5:19 PM
 */
public class ConfigSingleton {
    private static Configuration cfg = null;
    public static Configuration getConfig(ServletContext sc) {
        if (cfg == null){
            cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setServletContextForTemplateLoading(
                    sc,
                    "/WEB-INF/templates"
            );
            cfg.setDefaultEncoding("utf-8");
            cfg.setTemplateExceptionHandler(
                    TemplateExceptionHandler.HTML_DEBUG_HANDLER
            );
        }
        return cfg;
    }
}