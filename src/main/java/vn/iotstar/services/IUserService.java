package vn.iotstar.services;

import java.util.List;
import vn.iotstar.entity.User;

public interface IUserService {

    User login(String username, String password) throws Exception;

    boolean register(String username, String password, String email, String fullname, String phone) throws Exception;

    boolean verifyOtp(String emailOrUsername, String otpCode) throws Exception;

    boolean sendForgotPasswordOtp(String emailOrUsername) throws Exception;

    boolean resetPasswordWithOtp(String emailOrUsername, String otpCode, String newPassword) throws Exception;

    boolean resendOtp(String emailOrUsername) throws Exception;

    User get(String username);

    User getByEmail(String email);

    User getByUsernameOrEmail(String val);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String username);

    boolean checkExistPhone(String phone);

    void insert(User user);

    void update(User user);

    List<User> findAll();
}
