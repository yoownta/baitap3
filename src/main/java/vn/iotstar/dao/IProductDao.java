package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.entity.Product;

public interface IProductDao {

    void insert(Product product);

    void update(Product product);

    void delete(int productId) throws Exception;

    Product findById(int productId);

    List<Product> findAll();

    // Hiển thị 10 sản phẩm mới nhất lên trang chủ
    List<Product> findTop10Latest();

    // Phân trang danh sách sản phẩm (ví dụ 6sp/trang)
    List<Product> findAll(int page, int pageSize);

    int count();

    List<Product> findByCategoryId(int categoryId, int page, int pageSize);

    int countByCategoryId(int categoryId);

    List<Product> searchByName(String keyword);
}
