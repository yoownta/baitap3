<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Trị SStore - Bảng Điều Khiển</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/admin.css?v=2026'/>">
</head>
<body>

    <div class="wrapper">
        <div class="sidebar">
            <div class="admin-profile">
                <div class="brand-title">👕 SStore Admin</div>
                <div class="avatar-circle">
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            ${sessionScope.account.userName.substring(0,1).toUpperCase()}
                        </c:when>
                        <c:otherwise>A</c:otherwise>
                    </c:choose>
                </div>
                <div class="admin-username">
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">${sessionScope.account.fullName}</c:when>
                        <c:otherwise>Administrator</c:otherwise>
                    </c:choose>
                </div>
                <span class="role-badge">Quản Trị Viên</span>
            </div>

            <ul>
                <div class="sidebar-section-label">📦 Quản Lý Sản Phẩm</div>
                <li>
                    <a href="<c:url value='/admin/products'/>">📦 Danh Sách Sản Phẩm</a>
                </li>
                <li class="sub-menu">
                    <a href="<c:url value='/admin/product/add'/>">↳ + Thêm Sản Phẩm</a>
                </li>

                <div class="sidebar-section-label">📁 Quản Lý Danh Mục</div>
                <li>
                    <a href="<c:url value='/admin/categories'/>">📁 Danh Sách Danh Mục</a>
                </li>
                <li class="sub-menu">
                    <a href="<c:url value='/admin/category/add'/>">↳ + Thêm Danh Mục</a>
                </li>

                <div class="sidebar-section-label">🌐 Điều Hướng</div>
                <li>
                    <a href="<c:url value='/home'/>">🏠 Trang Chủ Storefront</a>
                </li>
                <li>
                    <a href="<c:url value='/product'/>">🛍️ Xem Shop</a>
                </li>
                <li>
                    <a href="<c:url value='/logout'/>" class="danger-link">🚪 Đăng Xuất</a>
                </li>
            </ul>
        </div>

        <div class="main-content">
            <div class="header-top">
                <h1>⚙️ Bảng Điều Khiển Quản Trị SStore</h1>
                <div class="user-panel">
                    <a href="<c:url value='/home'/>" class="btn-header-home">🌐 Xem Website</a>
                    <span>Xin chào:</span>
                    <span class="user-badge">
                        <c:choose>
                            <c:when test="${not empty sessionScope.account}">${sessionScope.account.fullName}</c:when>
                            <c:otherwise>Admin</c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </div>

            <div class="content-body">
                <c:choose>
                    <c:when test="${not empty subPage}">
                        <jsp:include page="${subPage}" />
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="/views/admin/product-list.jsp" />
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

</body>
</html>
