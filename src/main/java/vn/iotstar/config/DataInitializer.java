package vn.iotstar.config;

import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;

public class DataInitializer {

    public static void main(String[] args) {
        initSampleData();
    }

    public static void initSampleData() {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        try {
            trans.begin();

            // Cập nhật các user cũ nếu status là null
            enma.createQuery("UPDATE User u SET u.status = 1 WHERE u.status IS NULL").executeUpdate();

            // 1. Khởi tạo tài khoản Admin và User nếu chưa có
            List<User> users = enma.createQuery("SELECT u FROM User u WHERE u.userName = :un", User.class)
                    .setParameter("un", "admin").getResultList();

            if (users.isEmpty()) {
                User admin = new User();
                admin.setUserName("admin");
                admin.setPassword("123456");
                admin.setEmail("admin@sstore.vn");
                admin.setFullName("Quản Trị Viên");
                admin.setPhone("0901234567");
                admin.setRoleid(1); // Admin
                admin.setStatus(1); // Đã kích hoạt
                admin.setCreatedDate(new Date());
                enma.persist(admin);
                System.out.println(">>> Đã tạo tài khoản admin mặc định: admin / 123456");
            }

            List<User> regularUsers = enma.createQuery("SELECT u FROM User u WHERE u.userName = :un", User.class)
                    .setParameter("un", "khachhang").getResultList();

            if (regularUsers.isEmpty()) {
                User user = new User();
                user.setUserName("khachhang");
                user.setPassword("123456");
                user.setEmail("khachhang@sstore.vn");
                user.setFullName("Nguyễn Văn Khách");
                user.setPhone("0987654321");
                user.setRoleid(2); // User
                user.setStatus(1); // Đã kích hoạt
                user.setCreatedDate(new Date());
                enma.persist(user);
                System.out.println(">>> Đã tạo tài khoản khách hàng mặc định: khachhang / 123456");
            }

            // 2. Khởi tạo Categories nếu chưa có
            List<Category> categories = enma.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            Category c1, c2, c3, c4, c5;

            if (categories.isEmpty()) {
                c1 = new Category(0, "Áo Sơ Mi Nam", "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500&auto=format&fit=crop&q=60", 1);
                c2 = new Category(0, "Áo Thun Unisex", "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=500&auto=format&fit=crop&q=60", 1);
                c3 = new Category(0, "Quần Jean & Denim", "https://images.unsplash.com/photo-1542272604-780c96856592?w=500&auto=format&fit=crop&q=60", 1);
                c4 = new Category(0, "Áo Khoác Bomber", "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500&auto=format&fit=crop&q=60", 1);
                c5 = new Category(0, "Phụ Kiện Thời Trang", "https://images.unsplash.com/photo-1523293182086-7651a899d37f?w=500&auto=format&fit=crop&q=60", 1);

                enma.persist(c1);
                enma.persist(c2);
                enma.persist(c3);
                enma.persist(c4);
                enma.persist(c5);
                System.out.println(">>> Đã tạo 5 Danh mục sản phẩm mẫu!");
            } else {
                c1 = categories.get(0);
                c2 = categories.size() > 1 ? categories.get(1) : c1;
                c3 = categories.size() > 2 ? categories.get(2) : c1;
                c4 = categories.size() > 3 ? categories.get(3) : c1;
                c5 = categories.size() > 4 ? categories.get(4) : c1;
            }

            // 3. Khởi tạo 12 Sản phẩm mẫu để phục vụ Top 10 và Phân trang 6sp/trang
            List<Product> products = enma.createQuery("SELECT p FROM Product p", Product.class).getResultList();
            if (products.size() < 10) {
                Product[] sampleProducts = new Product[] {
                    new Product(0, "Áo Sơ Mi Oxford Slim-Fit Trắng", 350000, 50, "Chất liệu cotton dệt Oxford cao cấp, thoáng mát, đứng form, thích hợp đi làm và dự tiệc.", "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500&auto=format&fit=crop&q=60", 1, new Date(), c1),
                    new Product(0, "Áo Sơ Mi Caro Vintage Cổ Điển", 390000, 35, "Thiết kế kẻ sọc caro thanh lịch, phong cách Hàn Quốc trẻ trung năng động.", "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=500&auto=format&fit=crop&q=60", 1, new Date(), c1),
                    new Product(0, "Áo Thun Cotton 100% Minimalist Đen", 220000, 80, "Vải cotton mềm mịn co giãn 4 chiều, thấm hút mồ hôi cực tốt, form rộng unisex.", "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=500&auto=format&fit=crop&q=60", 1, new Date(), c2),
                    new Product(0, "Áo Thun Graphic Phố Đô Thị", 250000, 45, "Họa tiết graphic in nhiệt phản quang bền bỉ, đậm chất streetwear thời thượng.", "https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=500&auto=format&fit=crop&q=60", 1, new Date(), c2),
                    new Product(0, "Áo Thun Polo Basic Phối Cổ", 280000, 60, "Áo polo lịch lãm, chất vải cá sấu dệt tổ ong thoáng khí tuyệt đối cho mùa hè.", "https://images.unsplash.com/photo-1581655353564-df123a1eb820?w=500&auto=format&fit=crop&q=60", 1, new Date(), c2),
                    new Product(0, "Quần Jean Xanh Đậm Regular Fit", 450000, 40, "Chất denim bền bỉ nhập khẩu, giữ form chuẩn sau nhiều lần giặt, đường may tỉ mỉ.", "https://images.unsplash.com/photo-1542272604-780c96856592?w=500&auto=format&fit=crop&q=60", 1, new Date(), c3),
                    new Product(0, "Quần Jean Rách Gối Streetwear", 490000, 25, "Phong cách phá cách cá tính, co giãn nhẹ giúp bạn thoải mái vận động suốt ngày dài.", "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=500&auto=format&fit=crop&q=60", 1, new Date(), c3),
                    new Product(0, "Áo Khoác Bomber Kaki Dày Dặn", 550000, 30, "Lớp lót dù cản gió chống thấm nước nhẹ, khóa kéo kim loại mạ đồng chắc chắn.", "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500&auto=format&fit=crop&q=60", 1, new Date(), c4),
                    new Product(0, "Áo Khoác Da Biker Jacket Phong Trần", 890000, 15, "Da PU cao cấp không bong tróc, phom ôm vừa vặn tôn dáng phái mạnh.", "https://images.unsplash.com/photo-1520975916090-3105956dac38?w=500&auto=format&fit=crop&q=60", 1, new Date(), c4),
                    new Product(0, "Áo Khoác Gió Thể Thao 2 Lớp", 380000, 70, "Siêu nhẹ, chống tia UV hiệu quả, thích hợp mặc che nắng mùa hè và giữ ấm khi đi phượt.", "https://images.unsplash.com/photo-1548883354-7622d03aca27?w=500&auto=format&fit=crop&q=60", 1, new Date(), c4),
                    new Product(0, "Thắt Lưng Da Bò Khóa Tự Động", 190000, 90, "Mặt khóa hợp kim sang trọng chống gỉ sét, dây da bò nguyên tấm siêu bền.", "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500&auto=format&fit=crop&q=60", 1, new Date(), c5),
                    new Product(0, "Mũ Lưỡi Trai Kaki Thêu Chữ Nổi", 150000, 100, "Vải kaki dày dặn chuẩn form nón xuất khẩu, khóa chỉnh kích thước kim loại.", "https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=500&auto=format&fit=crop&q=60", 1, new Date(), c5),
                    new Product(0, "Ví Da Nam Đứng Đựng Thẻ & Tiền", 260000, 45, "Thiết kế nhỏ gọn nhiều ngăn tiện dụng, bảo vệ thẻ chống quét trộm RFID an toàn.", "https://images.unsplash.com/photo-1627123424574-724758594e93?w=500&auto=format&fit=crop&q=60", 1, new Date(), c5)
                };

                for (Product p : sampleProducts) {
                    enma.persist(p);
                }
                System.out.println(">>> Đã khởi tạo thành công 13 sản phẩm mẫu cho hệ thống!");
            }

            trans.commit();
            System.out.println(">>> HOÀN TẤT KHỞI TẠO DỮ LIỆU BAN ĐẦU CHO BAITAP3! <<<");
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
        } finally {
            enma.close();
        }
    }
}
