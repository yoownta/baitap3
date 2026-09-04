package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "[User]")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", columnDefinition = "nvarchar(150) not null")
    private String email;

    @Column(name = "username", columnDefinition = "nvarchar(50) not null")
    private String userName;

    @Column(name = "fullname", columnDefinition = "nvarchar(100) not null")
    private String fullName;

    @Column(name = "password", columnDefinition = "nvarchar(255) not null")
    private String password;

    @Column(name = "avatar", columnDefinition = "nvarchar(500) null")
    private String avatar;

    @Column(name = "roleid")
    private Integer roleid; // 1: Admin, 2: User / Customer

    @Column(name = "phone", columnDefinition = "nvarchar(20) null")
    private String phone;

    @Temporal(TemporalType.DATE)
    @Column(name = "createddate")
    private Date createdDate;

    @Column(name = "status")
    private Integer status; // 0: Chưa kích hoạt OTP, 1: Đã kích hoạt

    @Column(name = "code", columnDefinition = "nvarchar(10) null")
    private String code; // Mã OTP kích hoạt hoặc quên mật khẩu

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "codeExpiry")
    private Date codeExpiry; // Thời gian hết hạn của OTP

    public User() {
        this.createdDate = new Date();
        this.status = 0; // Mặc định chưa kích hoạt cho tới khi nhập OTP
    }

    public User(int id, String email, String userName, String fullName, String password, String avatar, Integer roleid,
            String phone, Date createdDate, Integer status, String code, Date codeExpiry) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.avatar = avatar;
        this.roleid = roleid != null ? roleid : 2;
        this.phone = phone;
        this.createdDate = (createdDate != null) ? createdDate : new Date();
        this.status = status != null ? status : 0;
        this.code = code;
        this.codeExpiry = codeExpiry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRoleid() {
        return roleid != null ? roleid : 2;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status != null ? status : 0;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCodeExpiry() {
        return codeExpiry;
    }

    public void setCodeExpiry(Date codeExpiry) {
        this.codeExpiry = codeExpiry;
    }
}
