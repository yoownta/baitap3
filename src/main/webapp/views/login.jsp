<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập Hệ Thống - SStore</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css?v=2026'/>">
</head>
<body>
    <div class="login-box">
        <div class="brand-header">
            <div class="brand-logo">👕</div>
            <h2>Đăng Nhập SStore</h2>
            <p class="subtitle">Chào mừng bạn quay trở lại với hệ thống</p>
        </div>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <c:if test="${alert != null}">
                <div class="alert-danger">
                    <span>⚠️</span>
                    <span>${alert}</span>
                </div>
            </c:if>

            <c:if test="${param.success != null}">
                <div class="alert-success">
                    <span>✅</span>
                    <span>${param.success}</span>
                </div>
            </c:if>

            <c:if test="${param.msg != null}">
                <div class="alert-success">
                    <span>✅</span>
                    <span>${param.msg}</span>
                </div>
            </c:if>

            <div class="input-group">
                <label class="input-label" for="username">Tên đăng nhập hoặc Email</label>
                <input type="text" id="username" placeholder="Nhập tài khoản của bạn..." name="username" value="${username}" class="form-control" required autofocus>
            </div>
            
            <div class="input-group">
                <label class="input-label" for="password">Mật khẩu</label>
                <input type="password" id="password" placeholder="Nhập mật khẩu..." name="password" class="form-control" required>
            </div>
            
            <div class="form-actions">
                <label style="display: flex; align-items: center; gap: 6px; cursor: pointer;">
                    <input type="checkbox" name="remember"> Ghi nhớ tài khoản
                </label>
                <a href="${pageContext.request.contextPath}/forgot-password" class="forgot-password">Quên mật khẩu?</a>
            </div>
            
            <button type="submit" class="logout-btn">Đăng Nhập Ngay</button>
            
            <div class="links">
                Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký mới</a>
            </div>
            
            <div class="links" style="margin-top: 15px; border-top: 1px solid #e2e8f0; padding-top: 15px;">
                <a href="${pageContext.request.contextPath}/home" style="color: #64748b; font-weight: 500;">&larr; Quay về Trang Chủ</a>
            </div>
        </form>
    </div>
</body>
</html>
