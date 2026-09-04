package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.services.CategoryServiceImpl;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.ProductServiceImpl;

@WebServlet(urlPatterns = { "/home" })
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IProductService productService = new ProductServiceImpl();
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 1. Lấy 10 sản phẩm mới nhất để hiển thị lên Trang chủ
        List<Product> top10Products = productService.findTop10Latest();

        // 2. Lấy danh sách danh mục để hiển thị thanh điều hướng/danh mục
        List<Category> categories = categoryService.findAll();

        req.setAttribute("top10Products", top10Products);
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}
