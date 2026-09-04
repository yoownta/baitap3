package vn.iotstar.services;

import java.util.Properties;
import java.util.Random;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import vn.iotstar.util.Constant;

/**
 * EmailService - Dịch vụ gửi OTP qua Email
 *
 * ╔══════════════════════════════════════════════════════════════╗
 *  THIẾT LẬP GMAIL APP PASSWORD:
 *  1. Vào https://myaccount.google.com/security
 *  2. Bật "Xác minh 2 bước"
 *  3. Vào "Mật khẩu ứng dụng" → Tạo app password mới
 *  4. Copy 16 ký tự → Dán vào Constant.APP_PASSWORD
 *  5. Điền email của bạn vào Constant.APP_EMAIL
 * ╚══════════════════════════════════════════════════════════════╝
 */
public class EmailService {

    /**
     * Sinh mã OTP ngẫu nhiên 6 chữ số
     */
    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Gửi email OTP đến địa chỉ email của user.
     * Nếu SMTP chưa được cấu hình, mã OTP sẽ được in ra Console để test.
     *
     * @param toEmail     Địa chỉ email người nhận
     * @param otpCode     Mã OTP 6 chữ số
     * @param subject     Tiêu đề email
     * @param actionTitle Mô tả hành động (vd: "Kích Hoạt Tài Khoản")
     * @return true nếu gửi thành công hoặc in ra console
     */
    public static boolean sendOtpEmail(String toEmail, String otpCode, String subject, String actionTitle) {
        // === LUÔN IN OTP RA CONSOLE (HỮU ÍCH KHI CHƯA CÓ SMTP) ===
        String border = "═".repeat(62);
        System.out.println("\n╔" + border + "╗");
        System.out.println("║            📧 SStore OTP NOTIFICATION                       ║");
        System.out.println("╠" + border + "╣");
        System.out.printf("║  Gửi đến  : %-47s ║%n", toEmail);
        System.out.printf("║  Hành động: %-47s ║%n", actionTitle);
        System.out.println("╠" + border + "╣");
        System.out.printf("║  🔑 MÃ OTP : %-47s ║%n", "[ " + otpCode + " ]  (Hiệu lực 15 phút)");
        System.out.println("╠" + border + "╣");
        System.out.println("║  ⚠ Nếu chưa nhận email: sao chép mã trên để xác thực.      ║");
        System.out.println("╚" + border + "╝\n");

        // === KIỂM TRA CẤU HÌNH EMAIL ===
        boolean emailConfigured = Constant.APP_EMAIL != null
                && !Constant.APP_EMAIL.trim().isEmpty()
                && !Constant.APP_EMAIL.equals("your_email@gmail.com")
                && Constant.APP_PASSWORD != null
                && !Constant.APP_PASSWORD.equals("xxxx xxxx xxxx xxxx")
                && Constant.APP_PASSWORD.length() >= 16;

        if (!emailConfigured) {
            System.out.println("[EMAIL SERVICE] Chưa cấu hình SMTP. Mã OTP đã được in ra Console Tomcat ở trên.");
            System.out.println("[EMAIL SERVICE] Hướng dẫn: Cập nhật APP_EMAIL và APP_PASSWORD trong Constant.java");
            return true; // Trả về true để user có thể dùng OTP từ console
        }

        // === GỬI EMAIL QUA SMTP (TRONG BACKGROUND THREAD) ===
        final String appPass = Constant.APP_PASSWORD.replaceAll("\\s+", ""); // Bỏ khoảng trắng nếu có
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", Constant.HOST_NAME);
            props.put("mail.smtp.port", String.valueOf(Constant.TSL_PORT));
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.connectiontimeout", "10000");
            props.put("mail.smtp.timeout", "10000");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Constant.APP_EMAIL, appPass);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constant.APP_EMAIL, "SStore - Hệ Thống Thời Trang"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setContent(buildEmailHtml(otpCode, actionTitle), "text/html; charset=UTF-8");

            // Gửi trong thread nền để không block web request
            new Thread(() -> {
                try {
                    Transport.send(message);
                    System.out.println("[EMAIL SUCCESS] ✅ Đã gửi OTP tới: " + toEmail);
                } catch (Exception ex) {
                    System.err.println("[EMAIL ERROR] ❌ Không thể gửi qua SMTP: " + ex.getMessage());
                    System.err.println("[EMAIL ERROR] → Kiểm tra lại APP_EMAIL và APP_PASSWORD trong Constant.java");
                    System.err.println("[EMAIL ERROR] → Đảm bảo đã bật 2-Step Verification và tạo App Password đúng cách");
                }
            }).start();

            return true;
        } catch (Exception e) {
            System.err.println("[EMAIL ERROR] Lỗi khởi tạo email session: " + e.getMessage());
            return true; // Vẫn return true vì OTP đã in ra console
        }
    }

    /**
     * Tạo nội dung HTML của email OTP
     */
    private static String buildEmailHtml(String otpCode, String actionTitle) {
        return "<div style=\"font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto; background: #f8fafc;\">"
            + "<div style=\"background: linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%); padding: 30px 35px; text-align: center; border-radius: 12px 12px 0 0;\">"
            + "<h1 style=\"color: #ffffff; margin: 0; font-size: 28px; font-weight: 900; letter-spacing: -0.5px;\">👕 SStore</h1>"
            + "<p style=\"color: rgba(255,255,255,0.85); margin: 8px 0 0; font-size: 15px;\">" + actionTitle + "</p>"
            + "</div>"
            + "<div style=\"background: #ffffff; padding: 35px; border: 1px solid #e2e8f0; border-top: none;\">"
            + "<p style=\"color: #334155; font-size: 16px; margin-bottom: 20px;\">Xin chào quý khách,</p>"
            + "<p style=\"color: #475569; font-size: 15px; line-height: 1.6;\">Đây là mã OTP để xác thực <strong style=\"color: #1e3a8a;\">" + actionTitle + "</strong> tại SStore:</p>"
            + "<div style=\"text-align: center; margin: 30px 0;\">"
            + "<div style=\"display: inline-block; background: linear-gradient(135deg, #eff6ff, #dbeafe); border: 2px dashed #3b82f6; border-radius: 16px; padding: 20px 40px;\">"
            + "<div style=\"font-size: 42px; font-weight: 900; letter-spacing: 12px; color: #1e40af; font-family: 'Courier New', monospace;\">" + otpCode + "</div>"
            + "<div style=\"color: #64748b; font-size: 13px; margin-top: 8px;\">Hiệu lực trong <strong>15 phút</strong></div>"
            + "</div></div>"
            + "<div style=\"background: #fef2f2; border: 1px solid #fecaca; border-radius: 8px; padding: 14px 18px; margin: 20px 0;\">"
            + "<p style=\"color: #991b1b; font-size: 13px; margin: 0;\">⚠️ Tuyệt đối không chia sẻ mã OTP này cho bất kỳ ai. SStore sẽ không bao giờ hỏi bạn về mã OTP qua điện thoại hoặc email.</p>"
            + "</div>"
            + "</div>"
            + "<div style=\"background: #f8fafc; padding: 20px 35px; text-align: center; border: 1px solid #e2e8f0; border-top: none; border-radius: 0 0 12px 12px;\">"
            + "<p style=\"color: #94a3b8; font-size: 12px; margin: 0;\">Email tự động từ SStore Fashion. Vui lòng không phản hồi email này.</p>"
            + "</div>"
            + "</div>";
    }
}
