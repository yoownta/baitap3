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

@WebServlet(urlPatterns = { "/product", "/product/detail", "/product-detail" })
public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IProductService productService = new ProductServiceImpl();
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String idParam = req.getParameter("id");

        // 1. Xem chi tiết 01 sản phẩm khi bấm chuột vào sản phẩm đó trên trang chủ hoặc trang product
        if (uri.contains("/detail") || idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Product product = productService.findById(id);

                if (product != null) {
                    req.setAttribute("product", product);

                    // Lấy các sản phẩm liên quan cùng danh mục
                    if (product.getCategory() != null) {
                        List<Product> relatedProducts = productService.findByCategoryId(product.getCategory().getCategoryid(), 1, 4);
                        req.setAttribute("relatedProducts", relatedProducts);
                    }

                    req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/product");
            return;
        }

        // 2. Hiển thị tất cả sản phẩm được phân trang 6sp/trang trên URL /product
        int page = 1;
        int pageSize = 6; // ĐÚNG YÊU CẦU: 6sp/trang

        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int cateId = 0;
        String cateParam = req.getParameter("category");
        if (cateParam != null && !cateParam.trim().isEmpty()) {
            try {
                cateId = Integer.parseInt(cateParam);
            } catch (NumberFormatException ignored) {
            }
        }

        int totalProducts;
        List<Product> listProduct;

        if (cateId > 0) {
            totalProducts = productService.countByCategoryId(cateId);
            listProduct = productService.findByCategoryId(cateId, page, pageSize);
        } else {
            totalProducts = productService.count();
            listProduct = productService.findAll(page, pageSize);
        }

        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }

        List<Category> categories = categoryService.findAll();

        req.setAttribute("listProduct", listProduct);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalProducts", totalProducts);
        req.setAttribute("categories", categories);
        req.setAttribute("selectedCateId", cateId);

        req.getRequestDispatcher("/views/product-list.jsp").forward(req, resp);
    }
}
