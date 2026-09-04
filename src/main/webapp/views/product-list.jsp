<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Sản Phẩm - SStore</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/storefront.css?v=2026'/>">
</head>
<body>

    <header class="navbar">
        <div class="nav-container">
            <a href="<c:url value='/home'/>" class="nav-brand">
                👕 SStore<span>.vn</span>
            </a>

            <ul class="nav-menu">
                <li class="nav-item"><a href="<c:url value='/home'/>">Trang Chủ</a></li>
                <li class="nav-item active"><a href="<c:url value='/product'/>">Tất Cả Sản Phẩm</a></li>
                <c:if test="${not empty sessionScope.account && sessionScope.account.roleid == 1}">
                    <li class="nav-item"><a href="<c:url value='/admin/products'/>" style="color: #dc2626; font-weight: 700;">⚙️ Trang Quản Trị</a></li>
                </c:if>
            </ul>

            <div class="nav-auth">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <div class="user-profile-badge">
                            <span>👤 ${sessionScope.account.fullName}</span>
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

    <main class="main-store-container">
        <div class="section-header">
            <div>
                <h1 class="section-title">
                    📦 Tất Cả Sản Phẩm
                    <span class="section-badge">Bộ Sưu Tập</span>
                </h1>
                <p style="color: #64748b; font-size: 14px; margin-top: 4px;">
                    Hiển thị các sản phẩm thời trang chất lượng cao từ SStore
                </p>
            </div>
            <span class="product-count-badge">Tổng số: <strong>${totalProducts}</strong> sản phẩm</span>
        </div>

        <div class="filter-bar">
            <div class="filter-pills">
                <a href="<c:url value='/product'/>" class="filter-pill ${empty selectedCateId || selectedCateId == 0 ? 'active' : ''}">
                    Tất cả danh mục
                </a>
                <c:forEach items="${categories}" var="cat">
                    <a href="<c:url value='/product?category=${cat.categoryid}'/>" class="filter-pill ${selectedCateId == cat.categoryid ? 'active' : ''}">
                        ${cat.categoryname}
                    </a>
                </c:forEach>
            </div>
            <div style="font-size: 13px; color: #64748b;">
                Trang <strong>${currentPage}</strong> / <strong>${totalPages}</strong>
            </div>
        </div>

        <div class="product-grid">
            <c:forEach items="${listProduct}" var="prod">
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

            <c:if test="${empty listProduct}">
                <div style="grid-column: 1 / -1; text-align: center; padding: 60px; background: #fff; border-radius: 12px; color: #94a3b8;">
                    <p style="font-size: 18px; margin-bottom: 10px;">Không có sản phẩm nào trong danh mục này.</p>
                    <a href="<c:url value='/product'/>" class="filter-pill active" style="display: inline-block;">Quay lại xem tất cả</a>
                </div>
            </c:if>
        </div>

        <c:if test="${totalPages > 1}">
            <div class="pagination-wrapper">
                <c:choose>
                    <c:when test="${currentPage > 1}">
                        <a href="<c:url value='/product?page=${currentPage - 1}${selectedCateId > 0 ? \"&category=\".concat(selectedCateId) : \"\"}'/>" class="page-link">
                            &laquo; Trước
                        </a>
                    </c:when>
                    <c:otherwise>
                        <span class="page-link disabled">&laquo; Trước</span>
                    </c:otherwise>
                </c:choose>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="<c:url value='/product?page=${i}${selectedCateId > 0 ? \"&category=\".concat(selectedCateId) : \"\"}'/>" class="page-link ${currentPage == i ? 'active' : ''}">
                        ${i}
                    </a>
                </c:forEach>

                <c:choose>
                    <c:when test="${currentPage < totalPages}">
                        <a href="<c:url value='/product?page=${currentPage + 1}${selectedCateId > 0 ? \"&category=\".concat(selectedCateId) : \"\"}'/>" class="page-link">
                            Sau &raquo;
                        </a>
                    </c:when>
                    <c:otherwise>
                        <span class="page-link disabled">Sau &raquo;</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
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
