package app; /**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 8:14 PM
 * 11-501
 * Task
 */

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 8:14 PM
 * 11-501
 */
public class Helpers {
    public static void render(ServletContext sc, HttpServletResponse response, String templateName, Object dataModel)
            throws IOException {

        Template tmpl = ConfigSingleton.getConfig(sc).getTemplate(templateName);
        try {
            tmpl.process(dataModel, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}
