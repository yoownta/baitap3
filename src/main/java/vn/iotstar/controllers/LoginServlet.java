package vn.iotstar.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.UserServiceImpl;

@WebServlet(urlPatterns = {
    "/login",
    "/register",
    "/verify-otp",
    "/resend-otp",
    "/forgot-password",
    "/reset-password",
    "/waiting",
    "/logout"
})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/login":
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("username".equals(cookie.getName())) {
                            req.setAttribute("username", cookie.getValue());
                            break;
                        }
                    }
                }
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                break;

            case "/register":
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
                break;

            case "/verify-otp":
                String emailVerify = req.getParameter("email");
                req.setAttribute("email", emailVerify);
                req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
                break;

            case "/resend-otp":
                String emailResend = req.getParameter("email");
                try {
                    if (emailResend != null && !emailResend.trim().isEmpty()) {
                        userService.resendOtp(emailResend.trim());
                    }
                    resp.sendRedirect(req.getContextPath() + "/verify-otp?email="
                            + URLEncoder.encode(emailResend, StandardCharsets.UTF_8)
                            + "&success=" + URLEncoder.encode("Đã gửi lại mã OTP mới đến email của bạn!", StandardCharsets.UTF_8));
                } catch (Exception e) {
                    resp.sendRedirect(req.getContextPath() + "/verify-otp?email="
                            + URLEncoder.encode(emailResend, StandardCharsets.UTF_8)
                            + "&error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
                }
                break;

            case "/forgot-password":
                req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
                break;

            case "/reset-password":
                String emailReset = req.getParameter("email");
                req.setAttribute("email", emailReset);
                req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
                break;

            case "/waiting":
                HttpSession session = req.getSession(false);
                if (session != null && session.getAttribute("account") != null) {
                    User u = (User) session.getAttribute("account");
                    if (u.getRoleid() == 1) {
                        resp.sendRedirect(req.getContextPath() + "/admin/products");
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/home");
                    }
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
                break;

            case "/logout":
                HttpSession sessionLogout = req.getSession(false);
                if (sessionLogout != null) {
                    sessionLogout.invalidate();
                }
                resp.sendRedirect(req.getContextPath() + "/login");
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/home");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        // 1. ĐĂNG NHẬP
        if ("/login".equals(path)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                req.setAttribute("alert", "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                return;
            }

            try {
                User user = userService.login(username.trim(), password.trim());
                if (user != null) {
                    HttpSession session = req.getSession(true);
                    session.setAttribute("account", user);

                    String remember = req.getParameter("remember");
                    Cookie ckUser = new Cookie("username", username.trim());
                    if (remember != null) {
                        ckUser.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                    } else {
                        ckUser.setMaxAge(0);
                    }
                    resp.addCookie(ckUser);

                    resp.sendRedirect(req.getContextPath() + "/waiting");
                    return;
                } else {
                    req.setAttribute("alert", "Tài khoản hoặc mật khẩu không chính xác!");
                    req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                }
            } catch (Exception e) {
                // Trường hợp tài khoản chưa kích hoạt OTP
                String errMsg = e.getMessage();
                if (errMsg != null && errMsg.contains("chưa được kích hoạt OTP")) {
                    User inactiveUser = userService.get(username.trim());
                    String targetEmail = (inactiveUser != null) ? inactiveUser.getEmail() : username.trim();
                    resp.sendRedirect(req.getContextPath() + "/verify-otp?email="
                            + URLEncoder.encode(targetEmail, StandardCharsets.UTF_8)
                            + "&error=" + URLEncoder.encode(errMsg, StandardCharsets.UTF_8));
                    return;
                }
                req.setAttribute("alert", errMsg);
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        }

        // 2. ĐĂNG KÝ + GỬI OTP KÍCH HOẠT
        else if ("/register".equals(path)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String fullname = req.getParameter("fullname");
            String phone = req.getParameter("phone");

            try {
                userService.register(username, password, email, fullname, phone);
                // Chuyển hướng người dùng đến trang xác thực OTP với thông báo
                resp.sendRedirect(req.getContextPath() + "/verify-otp?email="
                        + URLEncoder.encode(email, StandardCharsets.UTF_8)
                        + "&success=" + URLEncoder.encode("Đăng ký thành công! Vui lòng nhập mã OTP đã gửi qua email để kích hoạt tài khoản.", StandardCharsets.UTF_8));
            } catch (Exception e) {
                req.setAttribute("alert", e.getMessage());
                req.setAttribute("username", username);
                req.setAttribute("email", email);
                req.setAttribute("fullname", fullname);
                req.setAttribute("phone", phone);
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            }
        }

        // 3. XÁC THỰC MÃ OTP ĐỂ KÍCH HOẠT TÀI KHOẢN
        else if ("/verify-otp".equals(path)) {
            String email = req.getParameter("email");
            String otpCode = req.getParameter("otpCode");

            try {
                if (otpCode == null || otpCode.trim().isEmpty()) {
                    throw new Exception("Vui lòng nhập mã OTP 6 chữ số!");
                }
                userService.verifyOtp(email, otpCode);
                // Kích hoạt thành công -> chuyển về trang đăng nhập
                resp.sendRedirect(req.getContextPath() + "/login?success="
                        + URLEncoder.encode("Tài khoản của bạn đã được kích hoạt thành công! Hãy đăng nhập.", StandardCharsets.UTF_8));
            } catch (Exception e) {
                req.setAttribute("alert", e.getMessage());
                req.setAttribute("email", email);
                req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            }
        }

        // 4. QUÊN MẬT KHẨU - GỬI OTP QUA EMAIL
        else if ("/forgot-password".equals(path)) {
            String emailOrUsername = req.getParameter("email");

            try {
                if (emailOrUsername == null || emailOrUsername.trim().isEmpty()) {
                    throw new Exception("Vui lòng nhập Email hoặc Tài khoản!");
                }
                userService.sendForgotPasswordOtp(emailOrUsername.trim());

                User u = userService.getByUsernameOrEmail(emailOrUsername.trim());
                String targetEmail = (u != null) ? u.getEmail() : emailOrUsername.trim();

                resp.sendRedirect(req.getContextPath() + "/reset-password?email="
                        + URLEncoder.encode(targetEmail, StandardCharsets.UTF_8)
                        + "&success=" + URLEncoder.encode("Mã OTP đặt lại mật khẩu đã được gửi đến email của bạn!", StandardCharsets.UTF_8));
            } catch (Exception e) {
                req.setAttribute("alert", e.getMessage());
                req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            }
        }

        // 5. ĐẶT LẠI MẬT KHẨU VỚI OTP
        else if ("/reset-password".equals(path)) {
            String email = req.getParameter("email");
            String otpCode = req.getParameter("otpCode");
            String newPassword = req.getParameter("newPassword");
            String confirmPassword = req.getParameter("confirmPassword");

            try {
                if (otpCode == null || otpCode.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
                    throw new Exception("Vui lòng điền đầy đủ mã OTP và mật khẩu mới!");
                }
                if (!newPassword.equals(confirmPassword)) {
                    throw new Exception("Mật khẩu xác nhận không khớp!");
                }

                userService.resetPasswordWithOtp(email, otpCode, newPassword);

                resp.sendRedirect(req.getContextPath() + "/login?success="
                        + URLEncoder.encode("Đặt lại mật khẩu thành công! Vui lòng đăng nhập bằng mật khẩu mới.", StandardCharsets.UTF_8));
            } catch (Exception e) {
                req.setAttribute("alert", e.getMessage());
                req.setAttribute("email", email);
                req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            }
        }
    }
}
