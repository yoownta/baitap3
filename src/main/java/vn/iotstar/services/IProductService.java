package vn.iotstar.services;

import java.util.List;
import vn.iotstar.entity.Product;

public interface IProductService {

    void insert(Product product);

    void update(Product product);

    void delete(int productId) throws Exception;

    Product findById(int productId);

    List<Product> findAll();

    // Lấy 10 sản phẩm mới nhất
    List<Product> findTop10Latest();

    // Phân trang sản phẩm (ví dụ 6 sp/trang)
    List<Product> findAll(int page, int pageSize);

    int count();

    List<Product> findByCategoryId(int categoryId, int page, int pageSize);

    int countByCategoryId(int categoryId);

    List<Product> searchByName(String keyword);
}
