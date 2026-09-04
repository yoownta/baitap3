<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký Tài Khoản Mới - SStore</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css?v=2026'/>">
</head>
<body>
    <div class="form-container">
        <div class="brand-header">
            <div class="brand-logo">👕</div>
            <h2>Tạo Tài Khoản SStore</h2>
            <p class="subtitle">Đăng ký để nhận mã xác thực OTP kích hoạt qua Email</p>
        </div>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <c:if test="${alert != null}">
                <div class="alert-danger">
                    <span>⚠️</span>
                    <span>${alert}</span>
                </div>
            </c:if>

            <div class="input-group">
                <label class="input-label" for="username">Tên đăng nhập (Username)</label>
                <input type="text" id="username" placeholder="Nhập tên tài khoản..." name="username" value="${username}" class="form-control" required>
            </div>
            
            <div class="input-group">
                <label class="input-label" for="password">Mật khẩu</label>
                <input type="password" id="password" placeholder="Tối thiểu 6 ký tự..." name="password" class="form-control" required>
            </div>
            
            <div class="input-group">
                <label class="input-label" for="email">Địa chỉ Email (Nhận mã OTP kích hoạt)</label>
                <input type="email" id="email" placeholder="example@gmail.com" name="email" value="${email}" class="form-control" required>
            </div>
            
            <div class="input-group">
                <label class="input-label" for="fullname">Họ và tên</label>
                <input type="text" id="fullname" placeholder="Nguyễn Văn A" name="fullname" value="${fullname}" class="form-control" required>
            </div>
            
            <div class="input-group">
                <label class="input-label" for="phone">Số điện thoại</label>
                <input type="text" id="phone" placeholder="0901234567" name="phone" value="${phone}" class="form-control" required>
            </div>
            
            <button type="submit" class="logout-btn">Tiếp Tục &amp; Nhận Mã OTP</button>
            
            <div class="links">
                Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
            </div>

            <div class="links" style="margin-top: 15px; border-top: 1px solid #e2e8f0; padding-top: 15px;">
                <a href="${pageContext.request.contextPath}/home" style="color: #64748b; font-weight: 500;">&larr; Quay về Trang Chủ</a>
            </div>
        </form>
    </div>
</body>
</html>
