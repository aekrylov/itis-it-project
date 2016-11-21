package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.CommonHelpers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 9:06 PM
 */
@WebServlet("/files/*")
public class FileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File file = CommonHelpers.getImage(Paths.get(filename));
        if(!file.exists()) {
            response.sendError(404);
            return;
        }
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

}