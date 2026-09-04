<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực OTP Kích Hoạt Tài Khoản - SStore</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css?v=2026'/>">
</head>
<body>
    <div class="form-container">
        <div class="brand-header">
            <div class="brand-logo" style="background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);">🔐</div>
            <h2>Kích Hoạt Tài Khoản</h2>
            <p class="subtitle">Nhập mã OTP 6 chữ số vừa được gửi đến email của bạn</p>
        </div>

        <form action="${pageContext.request.contextPath}/verify-otp" method="post">
            <c:if test="${alert != null}">
                <div class="alert-danger">
                    <span>⚠️</span>
                    <span>${alert}</span>
                </div>
            </c:if>

            <c:if test="${param.error != null}">
                <div class="alert-danger">
                    <span>⚠️</span>
                    <span>${param.error}</span>
                </div>
            </c:if>

            <c:if test="${param.success != null}">
                <div class="alert-success">
                    <span>📩</span>
                    <span>${param.success}</span>
                </div>
            </c:if>

            <div class="input-group">
                <label class="input-label" for="email">Địa chỉ Email xác thực</label>
                <input type="email" id="email" name="email" value="${not empty email ? email : param.email}" class="form-control" required readonly style="background-color: #f1f5f9; cursor: not-allowed;">
            </div>
            
            <div class="input-group">
                <label class="input-label" for="otpCode" style="text-align: center; display: block;">MÃ OTP (6 CHỮ SỐ)</label>
                <input type="text" id="otpCode" placeholder="______" name="otpCode" maxlength="6" class="form-control otp-input-large" required autofocus autocomplete="off">
            </div>

            <p style="font-size: 13px; color: #64748b; text-align: center; margin-bottom: 20px;">
                Mã OTP có hiệu lực trong <strong>15 phút</strong>. Nếu chưa nhận được mail, hãy kiểm tra hòm thư Spam hoặc bấm gửi lại.
            </p>
            
            <button type="submit" class="logout-btn" style="background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);">
                Kích Hoạt Tài Khoản Ngay
            </button>
            
            <div style="margin-top: 15px; text-align: center;">
                <a href="${pageContext.request.contextPath}/resend-otp?email=${not empty email ? email : param.email}" class="btn-secondary" style="display: inline-block; width: auto; padding: 8px 18px;">
                    🔄 Gửi Lại Mã OTP
                </a>
            </div>

            <div class="links">
                Đã có tài khoản đã kích hoạt? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </div>
        </form>
    </div>
</body>
</html>
