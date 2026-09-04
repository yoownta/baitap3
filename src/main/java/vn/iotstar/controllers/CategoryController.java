package vn.iotstar.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.services.CategoryServiceImpl;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.util.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet(urlPatterns = {
    "/admin/categories",
    "/admin/category/list",
    "/admin/category/add",
    "/admin/category/insert",
    "/admin/category/edit",
    "/admin/category/update",
    "/admin/category/delete"
})
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ICategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();

        if (url.contains("/admin/categories") || url.contains("/admin/category/list")) {
            List<Category> list = new ArrayList<>();
            try {
                list = cateService.findAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.setAttribute("listcate", list);
            req.setAttribute("subPage", "/views/admin/category-list.jsp");
            req.getRequestDispatcher("/views/admin/layout/admin.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/add")) {
            req.setAttribute("subPage", "/views/admin/category-add.jsp");
            req.getRequestDispatcher("/views/admin/layout/admin.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Category category = null;
            try {
                category = cateService.findById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.setAttribute("cate", category);
            req.setAttribute("subPage", "/views/admin/category-edit.jsp");
            req.getRequestDispatcher("/views/admin/layout/admin.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                cateService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();

        if (url.contains("/admin/category/insert")) {
            String categoryname = req.getParameter("categoryname");
            int status = 1;
            try {
                status = Integer.parseInt(req.getParameter("status"));
            } catch (Exception ignored) {
            }
            String images = req.getParameter("images");

            Category category = new Category();
            category.setCategoryname(categoryname);
            category.setStatus(status);

            String fname = "";
            String uploadPath = Constant.DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            try {
                Part part = req.getPart("images1");
                if (part != null && part.getSize() > 0) {
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    int index = filename.lastIndexOf(".");
                    String ext = (index >= 0) ? filename.substring(index + 1) : "png";
                    fname = "cat_" + System.currentTimeMillis() + "." + ext;
                    part.write(uploadPath + File.separator + fname);
                    category.setImages(fname);
                } else if (images != null && !images.trim().isEmpty()) {
                    category.setImages(images.trim());
                } else {
                    category.setImages("https://placehold.co/80x60?text=Category");
                }
            } catch (FileNotFoundException fne) {
                fne.printStackTrace();
            }

            try {
                cateService.insert(category);
            } catch (Exception e) {
                e.printStackTrace();
            }

            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }

        if (url.contains("/admin/category/update")) {
            int categoryid = Integer.parseInt(req.getParameter("categoryid"));
            String categoryname = req.getParameter("categoryname");
            int status = 1;
            try {
                status = Integer.parseInt(req.getParameter("status"));
            } catch (Exception ignored) {
            }
            String images = req.getParameter("images");

            Category category = null;
            try {
                category = cateService.findById(categoryid);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (category != null) {
                String fileold = category.getImages();
                category.setCategoryname(categoryname);
                category.setStatus(status);

                String fname = "";
                String uploadPath = Constant.DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                try {
                    Part part = req.getPart("images1");
                    if (part != null && part.getSize() > 0) {
                        if (fileold != null && !fileold.startsWith("http") && !fileold.equals("avatar.png")) {
                            File oldFile = new File(uploadPath + File.separator + fileold);
                            if (oldFile.exists()) {
                                oldFile.delete();
                            }
                        }

                        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        int index = filename.lastIndexOf(".");
                        String ext = (index >= 0) ? filename.substring(index + 1) : "png";
                        fname = "cat_" + System.currentTimeMillis() + "." + ext;
                        part.write(uploadPath + File.separator + fname);
                        category.setImages(fname);
                    } else if (images != null && !images.trim().isEmpty()) {
                        category.setImages(images.trim());
                    } else {
                        category.setImages(fileold);
                    }
                } catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }

                try {
                    cateService.update(category);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }
}
