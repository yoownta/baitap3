package vn.iotstar.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.services.CategoryServiceImpl;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.ProductServiceImpl;
import vn.iotstar.util.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet(urlPatterns = {
    "/admin/products",
    "/admin/product/list",
    "/admin/product/add",
    "/admin/product/insert",
    "/admin/product/edit",
    "/admin/product/update",
    "/admin/product/delete"
})
public class AdminProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IProductService productService = new ProductServiceImpl();
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();

        if (url.contains("/admin/products") || url.contains("/admin/product/list")) {
            List<Product> list = productService.findAll();
            req.setAttribute("listproduct", list);
            req.setAttribute("subPage", "/views/admin/product-list.jsp");
            req.getRequestDispatcher("/views/admin/layout/admin.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/add")) {
            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories", categories);
            req.setAttribute("subPage", "/views/admin/product-add.jsp");
            req.getRequestDispatcher("/views/admin/layout/admin.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById(id);
            List<Category> categories = categoryService.findAll();
            req.setAttribute("product", product);
            req.setAttribute("categories", categories);
            req.setAttribute("subPage", "/views/admin/product-edit.jsp");
            req.getRequestDispatcher("/views/admin/layout/admin.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/delete")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                productService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();

        // 1. THÊM SẢN PHẨM MỚI
        if (url.contains("/admin/product/insert")) {
            String productName = req.getParameter("productName");
            double price = Double.parseDouble(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String description = req.getParameter("description");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            int status = Integer.parseInt(req.getParameter("status"));
            String images = req.getParameter("images");

            Product product = new Product();
            product.setProductName(productName);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setStatus(status);
            product.setCreateDate(new Date());

            Category category = categoryService.findById(categoryId);
            product.setCategory(category);

            // Xử lý upload ảnh
            String uploadPath = Constant.DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            try {
                Part part = req.getPart("imageFile");
                if (part != null && part.getSize() > 0) {
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    int index = filename.lastIndexOf(".");
                    String ext = (index >= 0) ? filename.substring(index + 1) : "png";
                    String fname = "prod_" + System.currentTimeMillis() + "." + ext;
                    part.write(uploadPath + File.separator + fname);
                    product.setImages(fname);
                } else if (images != null && !images.trim().isEmpty()) {
                    product.setImages(images.trim());
                } else {
                    product.setImages("https://placehold.co/600x400?text=Product+Image");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                productService.insert(product);
            } catch (Exception e) {
                e.printStackTrace();
            }

            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }

        // 2. CẬP NHẬT SẢN PHẨM
        else if (url.contains("/admin/product/update")) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            String productName = req.getParameter("productName");
            double price = Double.parseDouble(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String description = req.getParameter("description");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            int status = Integer.parseInt(req.getParameter("status"));
            String images = req.getParameter("images");

            Product product = productService.findById(productId);
            if (product != null) {
                String oldImage = product.getImages();
                product.setProductName(productName);
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setDescription(description);
                product.setStatus(status);

                Category category = categoryService.findById(categoryId);
                product.setCategory(category);

                String uploadPath = Constant.DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                try {
                    Part part = req.getPart("imageFile");
                    if (part != null && part.getSize() > 0) {
                        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        int index = filename.lastIndexOf(".");
                        String ext = (index >= 0) ? filename.substring(index + 1) : "png";
                        String fname = "prod_" + System.currentTimeMillis() + "." + ext;
                        part.write(uploadPath + File.separator + fname);
                        product.setImages(fname);
                    } else if (images != null && !images.trim().isEmpty()) {
                        product.setImages(images.trim());
                    } else {
                        product.setImages(oldImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    productService.update(product);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }
}
