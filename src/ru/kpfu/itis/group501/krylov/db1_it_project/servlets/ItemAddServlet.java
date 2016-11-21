package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.CommonHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Post;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Product;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ParameterMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 11:04 AM
 */
@MultipartConfig
public class ItemAddServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //render form
        CommonHelpers.render(getServletContext(), req, resp, "new_product.ftl", null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        //add product and redirect to its page
        ParameterMap map = getParameterMap(req);

        try {
            Product product = postService.toProduct(map);
            postService.createProduct(product);
            Post post = new Post(product, CommonHelpers.getCurrentUser(req));
            postService.createPost(post);

            Part image = req.getPart("image");
            CommonHelpers.saveImage(Paths.get("products", String.valueOf(product.getId()), "1"),
                    image.getInputStream());

            resp.sendRedirect("/item?id="+post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            Map<String, String> pass = new HashMap<>();
            pass.put("error", "db");
            redirect(req, resp, pass);
        }

    }
}
