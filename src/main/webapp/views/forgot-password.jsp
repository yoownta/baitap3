<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu - SStore</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css?v=2026'/>">
</head>
<body>
    <div class="form-container">
        <div class="brand-header">
            <div class="brand-logo" style="background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);">🔑</div>
            <h2>Quên Mật Khẩu?</h2>
            <p class="subtitle">Nhập email hoặc tài khoản để nhận mã OTP đặt lại mật khẩu</p>
        </div>

        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
            <c:if test="${alert != null}">
                <div class="alert-danger">
                    <span>⚠️</span>
                    <span>${alert}</span>
                </div>
            </c:if>

            <div class="input-group">
                <label class="input-label" for="email">Email hoặc Tên đăng nhập đã đăng ký</label>
                <input type="text" id="email" placeholder="example@gmail.com hoặc username" name="email" class="form-control" required autofocus>
            </div>

            <p style="font-size: 13px; color: #64748b; margin-bottom: 20px;">
                Hệ thống sẽ gửi một mã OTP gồm 6 chữ số đến hộp thư của bạn để xác nhận yêu cầu đặt lại mật khẩu.
            </p>
            
            <button type="submit" class="logout-btn" style="background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);">
                Gửi Mã OTP Đặt Lại Mật Khẩu
            </button>
            
            <div class="links">
                Nhớ lại mật khẩu? <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
            </div>

            <div class="links" style="margin-top: 15px; border-top: 1px solid #e2e8f0; padding-top: 15px;">
                <a href="${pageContext.request.contextPath}/home" style="color: #64748b; font-weight: 500;">&larr; Quay về Trang Chủ</a>
            </div>
        </form>
    </div>
</body>
</html>
