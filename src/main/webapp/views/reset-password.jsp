<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Lại Mật Khẩu - SStore</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css?v=2026'/>">
</head>
<body>
    <div class="form-container">
        <div class="brand-header">
            <div class="brand-logo" style="background: linear-gradient(135deg, #6366f1 0%, #4f46e5 100%);">🔄</div>
            <h2>Đặt Lại Mật Khẩu</h2>
            <p class="subtitle">Nhập mã OTP đã nhận qua Email cùng mật khẩu mới của bạn</p>
        </div>

        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <c:if test="${alert != null}">
                <div class="alert-danger">
                    <span>⚠️</span>
                    <span>${alert}</span>
                </div>
            </c:if>

            <c:if test="${param.success != null}">
                <div class="alert-success">
                    <span>📩</span>
                    <span>${param.success}</span>
                </div>
            </c:if>

            <div class="input-group">
                <label class="input-label" for="email">Tài khoản Email xác thực</label>
                <input type="email" id="email" name="email" value="${not empty email ? email : param.email}" class="form-control" required readonly style="background-color: #f1f5f9; cursor: not-allowed;">
            </div>

            <div class="input-group">
                <label class="input-label" for="otpCode" style="text-align: center; display: block;">MÃ OTP (6 CHỮ SỐ)</label>
                <input type="text" id="otpCode" placeholder="______" name="otpCode" maxlength="6" class="form-control otp-input-large" required autofocus autocomplete="off">
            </div>

            <div class="input-group">
                <label class="input-label" for="newPassword">Mật khẩu mới</label>
                <input type="password" id="newPassword" placeholder="Nhập mật khẩu mới..." name="newPassword" class="form-control" required>
            </div>

            <div class="input-group">
                <label class="input-label" for="confirmPassword">Xác nhận mật khẩu mới</label>
                <input type="password" id="confirmPassword" placeholder="Nhập lại mật khẩu mới..." name="confirmPassword" class="form-control" required>
            </div>
            
            <button type="submit" class="logout-btn" style="background: linear-gradient(135deg, #6366f1 0%, #4f46e5 100%);">
                Cập Nhật Mật Khẩu Mới
            </button>
            
            <div style="margin-top: 15px; text-align: center;">
                <a href="${pageContext.request.contextPath}/resend-otp?email=${not empty email ? email : param.email}" class="btn-secondary" style="display: inline-block; width: auto; padding: 8px 18px;">
                    🔄 Gửi Lại Mã OTP
                </a>
            </div>

            <div class="links">
                Quay lại <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </div>
        </form>
    </div>
</body>
</html>
