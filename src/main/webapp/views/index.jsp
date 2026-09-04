<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SStore - Hệ Thống Thời Trang Cao Cấp</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/storefront.css?v=2026'/>">
</head>
<body>

    <header class="navbar">
        <div class="nav-container">
            <a href="<c:url value='/home'/>" class="nav-brand">
                👕 SStore<span>.vn</span>
            </a>

            <ul class="nav-menu">
                <li class="nav-item active"><a href="<c:url value='/home'/>">Trang Chủ</a></li>
                <li class="nav-item"><a href="<c:url value='/product'/>">Tất Cả Sản Phẩm</a></li>
                <c:if test="${not empty sessionScope.account && sessionScope.account.roleid == 1}">
                    <li class="nav-item"><a href="<c:url value='/admin/products'/>" style="color: #dc2626; font-weight: 700;">⚙️ Trang Quản Trị</a></li>
                </c:if>
            </ul>

            <div class="nav-auth">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <div class="user-profile-badge">
                            <span>👤 ${sessionScope.account.fullName}</span>
                            <c:if test="${sessionScope.account.roleid == 1}">
                                <span style="background: #fee2e2; color: #991b1b; padding: 2px 8px; border-radius: 12px; font-size: 11px;">Admin</span>
                            </c:if>
                        </div>
                        <a href="<c:url value='/logout'/>" class="btn-nav-login" style="border-color: #ef4444; color: #ef4444;">Đăng Xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/login'/>" class="btn-nav-login">Đăng Nhập</a>
                        <a href="<c:url value='/register'/>" class="btn-nav-register">Đăng Ký</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </header>

    <section class="hero-section">
        <div class="hero-content">
            <span class="hero-tag">Bộ Sưu Tập Mới 2026</span>
            <h1 class="hero-title">Đẳng Cấp Thời Trang Cho Phong Cách Của Bạn</h1>
            <p class="hero-desc">Khám phá các mẫu thời trang cao cấp với chất liệu tuyển chọn, thiết kế hiện đại và thanh lịch nhất.</p>
            <a href="<c:url value='/product'/>" class="hero-btn">
                🛍️ Xem Ngay Tất Cả Sản Phẩm
            </a>
        </div>
    </section>

    <main class="main-store-container">
        <div class="section-header">
            <div>
                <h2 class="section-title">
                    🔥 10 Sản Phẩm Mới Nhất
                    <span class="section-badge">Mới Cập Nhật</span>
                </h2>
                <p style="color: #64748b; font-size: 14px; margin-top: 4px;">Danh sách 10 mẫu thời trang vừa cập bến tại cửa hàng</p>
            </div>
            <a href="<c:url value='/product'/>" class="view-all-link">
                Xem toàn bộ sản phẩm &rarr;
            </a>
        </div>

        <div class="product-grid">
            <c:forEach items="${top10Products}" var="prod">
                <div class="product-card">
                    <div class="product-thumb-wrap">
                        <c:choose>
                            <c:when test="${prod.images != null && prod.images.startsWith('http')}">
                                <c:url value="${prod.images}" var="pImg" />
                            </c:when>
                            <c:otherwise>
                                <c:url value="/image?fname=${prod.images}" var="pImg" />
                            </c:otherwise>
                        </c:choose>
                        <a href="<c:url value='/product/detail?id=${prod.productId}'/>">
                            <img src="${pImg}" alt="${prod.productName}" class="product-thumb" onerror="this.onerror=null;this.src='https://placehold.co/600x400?text=SStore+Product';" />
                        </a>
                        <c:if test="${prod.category != null}">
                            <span class="product-category-tag">${prod.category.categoryname}</span>
                        </c:if>
                    </div>

                    <div class="product-details">
                        <h3 class="product-title">
                            <a href="<c:url value='/product/detail?id=${prod.productId}'/>" title="${prod.productName}">
                                ${prod.productName}
                            </a>
                        </h3>

                        <div class="product-price-row">
                            <div class="product-price">
                                <fmt:formatNumber value="${prod.price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                            </div>
                            <a href="<c:url value='/product/detail?id=${prod.productId}'/>" class="btn-view-detail">
                                Xem Chi Tiết
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty top10Products}">
                <div style="grid-column: 1 / -1; text-align: center; padding: 60px; background: #fff; border-radius: 12px; color: #94a3b8;">
                    <p style="font-size: 18px; margin-bottom: 10px;">Chưa có sản phẩm nào được hiển thị.</p>
                </div>
            </c:if>
        </div>
    </main>

    <footer class="store-footer">
        <div class="footer-container">
            <div class="footer-brand">👕 SStore Fashion</div>
            <p>Hệ thống mua sắm thời trang trực tuyến tích hợp kiến trúc JPA Hibernate 6</p>
            <div class="footer-copy">
                &copy; 2026 SStore. All rights reserved.
            </div>
        </div>
    </footer>

</body>
</html>
