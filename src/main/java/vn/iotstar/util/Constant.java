package vn.iotstar.util;

public class Constant {

    // Thư mục lưu trữ hình ảnh upload
    public static final String DIR = "D:\\Web\\baitap3\\upload";

    // ================================================================
    // CẤU HÌNH EMAIL SMTP (Gmail)
    // Hướng dẫn lấy App Password Gmail:
    // 1. Truy cập https://myaccount.google.com/security
    // 2. Bật "Xác minh 2 bước" (2-Step Verification)
    // 3. Tìm "Mật khẩu ứng dụng" (App passwords)
    // 4. Chọn "Thư" (Mail) → Máy Windows → Tạo → Copy 16 ký tự
    // ================================================================
    public static final String HOST_NAME = "smtp.gmail.com";
    public static final int SSL_PORT = 465;
    public static final int TSL_PORT = 587;

    // *** THAY BỔ SUNG EMAIL VÀ APP PASSWORD CỦA BẠN VÀO ĐÂY ***
    public static final String APP_EMAIL = "luxiphot123456@gmail.com";
    public static final String APP_PASSWORD = "fykb tuva hyqr oksh"; // App Password 16 ký tự

    // *** NẾU CHƯA CÓ APP PASSWORD: OTP SẼ ĐƯỢC IN RA CONSOLE TOMCAT ***
    // Mở tab Logs trong IDE hoặc Console của Tomcat để xem mã OTP
}
