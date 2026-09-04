package vn.iotstar.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.util.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/image")
public class DownloadImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fileName = req.getParameter("fname");

        if (fileName != null && !fileName.trim().isEmpty()) {
            File file = new File(Constant.DIR + File.separator + fileName);

            if (file.exists()) {
                String mimeType = getServletContext().getMimeType(file.getName());
                if (mimeType == null) {
                    mimeType = "image/jpeg";
                }
                resp.setContentType(mimeType);
                Files.copy(file.toPath(), resp.getOutputStream());
            }
        }
    }
}
