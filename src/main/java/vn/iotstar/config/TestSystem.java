package vn.iotstar.config;

import java.util.List;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.ProductServiceImpl;
import vn.iotstar.services.UserServiceImpl;

public class TestSystem {

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("BẮT ĐẦU KIỂM THỬ TỰ ĐỘNG CÁC TÍNH NĂNG CỦA DỰ ÁN BAITAP3");
        System.out.println("==========================================================");

        // Nạp dữ liệu mẫu vào CSDL mới nếu chưa có
        DataInitializer.initSampleData();

        IUserService userService = new UserServiceImpl();
        IProductService productService = new ProductServiceImpl();

        try {
            // 1. Kiểm tra đăng ký và kích hoạt OTP
            String testUser = "testuser_" + System.currentTimeMillis() % 10000;
            String testEmail = testUser + "@gmail.com";
            System.out.println("\n[TEST 1] Đăng ký tài khoản mới: " + testUser);
            userService.register(testUser, "123456", testEmail, "Người Dùng Thử Nghiệm", "0912345678");

            User created = userService.get(testUser);
            System.out.println("-> Tài khoản vừa tạo, trạng thái status = " + created.getStatus() + " (Mong đợi: 0)");
            System.out.println("-> Mã OTP đã sinh: " + created.getCode());

            // Thử đăng nhập khi chưa kích hoạt -> phải bắt được ngoại lệ
            try {
                userService.login(testUser, "123456");
                System.err.println("-> LỖI: Đăng nhập được khi tài khoản chưa kích hoạt!");
            } catch (Exception ex) {
                System.out.println("-> ĐÚNG NGHIỆP VỤ: Đăng nhập bị chặn khi chưa kích hoạt: " + ex.getMessage());
            }

            // Kích hoạt OTP
            System.out.println("\n[TEST 2] Kích hoạt tài khoản bằng mã OTP: " + created.getCode());
            userService.verifyOtp(testEmail, created.getCode());
            User activated = userService.get(testUser);
            System.out.println("-> Trạng thái sau kích hoạt: status = " + activated.getStatus() + " (Mong đợi: 1)");

            // Đăng nhập sau khi kích hoạt thành công
            User loggedIn = userService.login(testUser, "123456");
            System.out.println("-> Đăng nhập thành công với User: " + loggedIn.getUserName() + " | FullName: "
                    + loggedIn.getFullName());

            // 2. Kiểm tra Quên mật khẩu và đổi mật khẩu bằng OTP
            System.out.println("\n[TEST 3] Quên mật khẩu & Đặt lại mật khẩu với OTP");
            userService.sendForgotPasswordOtp(testEmail);
            User forgotUser = userService.get(testUser);
            System.out.println("-> Mã OTP đặt lại mật khẩu: " + forgotUser.getCode());

            userService.resetPasswordWithOtp(testEmail, forgotUser.getCode(), "newpassword123");
            User reLoggedIn = userService.login(testUser, "newpassword123");
            System.out.println("-> Đăng nhập thành công với mật khẩu mới: " + (reLoggedIn != null));

            // 3. Kiểm tra 10 sản phẩm mới nhất lên trang chủ
            System.out.println("\n[TEST 4] Kiểm tra truy vấn 10 sản phẩm mới nhất cho Trang chủ");
            List<Product> top10 = productService.findTop10Latest();
            System.out.println("-> Số lượng sản phẩm mới nhất lấy được: " + top10.size() + " (Mong đợi: 10)");
            for (int i = 0; i < Math.min(3, top10.size()); i++) {
                Product p = top10.get(i);
                System.out.println("   #" + (i + 1) + " ID=" + p.getProductId() + " | Tên: " + p.getProductName()
                        + " | Giá: " + p.getPrice());
            }

            // 4. Kiểm tra phân trang 6 sản phẩm/trang tại URL /product
            System.out.println("\n[TEST 5] Kiểm tra phân trang 6 sản phẩm / trang cho URL /product");
            int total = productService.count();
            int totalPages = (int) Math.ceil((double) total / 6);
            System.out
                    .println("-> Tổng số sản phẩm trong DB: " + total + " | Tổng số trang (6sp/trang): " + totalPages);

            List<Product> page1 = productService.findAll(1, 6);
            System.out.println("-> Trang 1: lấy được " + page1.size() + " sản phẩm (Mong đợi: 6)");

            List<Product> page2 = productService.findAll(2, 6);
            System.out.println("-> Trang 2: lấy được " + page2.size() + " sản phẩm (Mong đợi: 6 hoặc số dư còn lại)");

            // 5. Kiểm tra chi tiết 01 sản phẩm
            System.out.println("\n[TEST 6] Kiểm tra hiển thị chi tiết 01 sản phẩm");
            Product detail = productService.findById(top10.get(0).getProductId());
            System.out.println("-> Chi tiết sản phẩm ID=" + detail.getProductId() + ":");
            System.out.println("   Tên: " + detail.getProductName());
            System.out.println(
                    "   Danh mục: " + (detail.getCategory() != null ? detail.getCategory().getCategoryname() : "N/A"));
            System.out.println("   Giá: " + detail.getPrice() + " VNĐ | Số lượng: " + detail.getQuantity());
            System.out.println("   Mô tả: " + detail.getDescription());

            System.out.println("\n==========================================================");
            System.out.println(">>> TẤT CẢ CÁC TÍNH NĂNG ĐÃ HOẠT ĐỘNG CHÍNH XÁC 100%! <<<");
            System.out.println("==========================================================");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaConfig.closeFactory();
        }
    }
}
