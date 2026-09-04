package vn.iotstar.services;

import java.util.Date;
import java.util.List;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.UserDaoImpl;
import vn.iotstar.entity.User;

public class UserServiceImpl implements IUserService {

    private IUserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) throws Exception {
        User user = userDao.findByUsername(username);
        if (user == null) {
            user = userDao.findByEmail(username);
        }

        if (user != null && password.equals(user.getPassword())) {
            if (user.getStatus() == 0) {
                throw new Exception("Tài khoản chưa được kích hoạt OTP. Vui lòng kiểm tra email và xác thực tài khoản!");
            }
            return user;
        }
        return null;
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) throws Exception {
        if (userDao.checkExistUsername(username)) {
            throw new Exception("Tài khoản đã tồn tại trong hệ thống!");
        }
        if (userDao.checkExistEmail(email)) {
            throw new Exception("Email đã được sử dụng bởi tài khoản khác!");
        }

        // Sinh mã OTP 6 chữ số
        String otp = EmailService.generateOtp();
        // Thời gian hết hạn sau 15 phút
        Date expiry = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setFullName(fullname);
        newUser.setPhone(phone);
        newUser.setRoleid(2); // 2: Khách hàng thông thường
        newUser.setStatus(0); // 0: Chờ xác thực kích hoạt OTP
        newUser.setCode(otp);
        newUser.setCodeExpiry(expiry);
        newUser.setCreatedDate(new Date());

        userDao.insert(newUser);

        // Gửi email OTP
        EmailService.sendOtpEmail(email, otp, "Xác Thực Kích Hoạt Tài Khoản - SStore", "Kích Hoạt Tài Khoản");

        return true;
    }

    @Override
    public boolean verifyOtp(String emailOrUsername, String otpCode) throws Exception {
        User user = userDao.findByUsernameOrEmail(emailOrUsername);
        if (user == null) {
            throw new Exception("Không tìm thấy thông tin tài khoản!");
        }

        if (user.getCode() == null || !user.getCode().trim().equals(otpCode.trim())) {
            throw new Exception("Mã OTP không chính xác, vui lòng thử lại!");
        }

        if (user.getCodeExpiry() != null && user.getCodeExpiry().before(new Date())) {
            throw new Exception("Mã OTP đã hết hiệu lực. Vui lòng bấm 'Gửi lại mã'!");
        }

        // Kích hoạt tài khoản và xóa mã OTP đã dùng
        user.setStatus(1);
        user.setCode(null);
        user.setCodeExpiry(null);
        userDao.update(user);

        return true;
    }

    @Override
    public boolean sendForgotPasswordOtp(String emailOrUsername) throws Exception {
        User user = userDao.findByUsernameOrEmail(emailOrUsername);
        if (user == null) {
            throw new Exception("Không tìm thấy tài khoản với email hoặc tên đăng nhập này!");
        }

        String otp = EmailService.generateOtp();
        Date expiry = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

        user.setCode(otp);
        user.setCodeExpiry(expiry);
        userDao.update(user);

        // Gửi email OTP đặt lại mật khẩu
        EmailService.sendOtpEmail(user.getEmail(), otp, "Mã OTP Đặt Lại Mật Khẩu - SStore", "Đặt Lại Mật Khẩu");

        return true;
    }

    @Override
    public boolean resetPasswordWithOtp(String emailOrUsername, String otpCode, String newPassword) throws Exception {
        User user = userDao.findByUsernameOrEmail(emailOrUsername);
        if (user == null) {
            throw new Exception("Không tìm thấy tài khoản!");
        }

        if (user.getCode() == null || !user.getCode().trim().equals(otpCode.trim())) {
            throw new Exception("Mã OTP không đúng!");
        }

        if (user.getCodeExpiry() != null && user.getCodeExpiry().before(new Date())) {
            throw new Exception("Mã OTP đã hết hiệu lực!");
        }

        user.setPassword(newPassword);
        user.setCode(null);
        user.setCodeExpiry(null);
        userDao.update(user);

        return true;
    }

    @Override
    public boolean resendOtp(String emailOrUsername) throws Exception {
        User user = userDao.findByUsernameOrEmail(emailOrUsername);
        if (user == null) {
            throw new Exception("Không tìm thấy tài khoản!");
        }

        String otp = EmailService.generateOtp();
        Date expiry = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

        user.setCode(otp);
        user.setCodeExpiry(expiry);
        userDao.update(user);

        String action = (user.getStatus() == 0) ? "Kích Hoạt Tài Khoản" : "Đặt Lại Mật Khẩu";
        EmailService.sendOtpEmail(user.getEmail(), otp, "Gửi Lại Mã OTP - SStore", action);

        return true;
    }

    @Override
    public User get(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User getByUsernameOrEmail(String val) {
        return userDao.findByUsernameOrEmail(val);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
