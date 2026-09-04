<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.productName} - Chi Tiết Sản Phẩm | SStore</title>
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
        <div class="detail-breadcrumb" style="margin-bottom: 25px;">
            <a href="<c:url value='/home'/>">Trang Chủ</a> &nbsp;&gt;&nbsp;
            <a href="<c:url value='/product'/>">Sản Phẩm</a> &nbsp;&gt;&nbsp;
            <c:if test="${product.category != null}">
                <a href="<c:url value='/product?category=${product.category.categoryid}'/>">${product.category.categoryname}</a> &nbsp;&gt;&nbsp;
            </c:if>
            <span style="color: #1e293b; font-weight: 600;">${product.productName}</span>
        </div>

        <div class="detail-card">
            <div class="detail-img-wrap">
                <c:choose>
                    <c:when test="${product.images != null && product.images.startsWith('http')}">
                        <c:url value="${product.images}" var="pDetailImg" />
                    </c:when>
                    <c:otherwise>
                        <c:url value="/image?fname=${product.images}" var="pDetailImg" />
                    </c:otherwise>
                </c:choose>
                <img src="${pDetailImg}" alt="${product.productName}" class="detail-img" onerror="this.onerror=null;this.src='https://placehold.co/600x600?text=SStore+Product';" />
            </div>

            <div class="detail-info">
                <div class="detail-meta">
                    <c:if test="${product.category != null}">
                        <span style="background: #e0f2fe; color: #0369a1; font-weight: 700; font-size: 13px; padding: 4px 12px; border-radius: 6px;">
                            📁 ${product.category.categoryname}
                        </span>
                    </c:if>
                    <span class="detail-badge-stock">
                        ✔ Còn hàng (${product.quantity} sản phẩm)
                    </span>
                </div>

                <h1 class="detail-title">${product.productName}</h1>

                <div class="detail-price-box">
                    <span style="font-size: 13px; color: #64748b; text-transform: uppercase; font-weight: 600; display: block; margin-bottom: 4px;">Giá niêm yết:</span>
                    <span class="detail-price">
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                    </span>
                </div>

                <div class="detail-desc-title">Mô Tả Sản Phẩm:</div>
                <div class="detail-desc-text">${product.description}</div>

                <div class="detail-actions">
                    <a href="javascript:void(0);" onclick="alert('Đã thêm sản phẩm [${product.productName}] vào giỏ hàng!');" class="btn-buy-now">
                        🛒 Thêm Vào Giỏ Hàng
                    </a>
                    <a href="<c:url value='/product'/>" class="btn-back-catalog">
                        Quay lại danh mục
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty relatedProducts}">
            <div class="section-header" style="margin-top: 50px;">
                <h2 class="section-title">
                    ✨ Sản Phẩm Cùng Danh Mục
                </h2>
                <a href="<c:url value='/product?category=${product.category.categoryid}'/>" class="view-all-link">
                    Xem tất cả trong danh mục &rarr;
                </a>
            </div>

            <div class="product-grid">
                <c:forEach items="${relatedProducts}" var="rel">
                    <c:if test="${rel.productId != product.productId}">
                        <div class="product-card">
                            <div class="product-thumb-wrap">
                                <c:choose>
                                    <c:when test="${rel.images != null && rel.images.startsWith('http')}">
                                        <c:url value="${rel.images}" var="relImg" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/image?fname=${rel.images}" var="relImg" />
                                    </c:otherwise>
                                </c:choose>
                                <a href="<c:url value='/product/detail?id=${rel.productId}'/>">
                                    <img src="${relImg}" alt="${rel.productName}" class="product-thumb" onerror="this.onerror=null;this.src='https://placehold.co/600x400?text=SStore+Product';" />
                                </a>
                            </div>

                            <div class="product-details">
                                <h3 class="product-title">
                                    <a href="<c:url value='/product/detail?id=${rel.productId}'/>" title="${rel.productName}">
                                        ${rel.productName}
                                    </a>
                                </h3>

                                <div class="product-price-row">
                                    <div class="product-price">
                                        <fmt:formatNumber value="${rel.price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                    </div>
                                    <a href="<c:url value='/product/detail?id=${rel.productId}'/>" class="btn-view-detail">
                                        Xem Chi Tiết
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
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
