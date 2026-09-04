<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="card-panel">
    <div class="card-header">
        <h2>📦 Danh Sách Sản Phẩm</h2>
        <a href="<c:url value='/admin/product/add'/>" class="btn-create">+ Thêm Sản Phẩm Mới</a>
    </div>
    
    <c:if test="${not empty param.success}">
        <div class="admin-alert-success" style="margin: 16px 24px 0;">
            ✅ ${param.success}
        </div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="admin-alert-danger" style="margin: 16px 24px 0;">
            ⚠️ ${param.error}
        </div>
    </c:if>
    
    <div class="card-body">
        <table class="table-custom">
            <thead>
                <tr>
                    <th style="width: 50px; text-align: center;">ID</th>
                    <th style="width: 90px; text-align: center;">Hình Ảnh</th>
                    <th>Tên Sản Phẩm</th>
                    <th style="width: 150px;">Danh Mục</th>
                    <th style="width: 140px;">Giá Bán</th>
                    <th style="width: 90px; text-align: center;">Số Lượng</th>
                    <th style="width: 120px; text-align: center;">Trạng Thái</th>
                    <th style="width: 150px; text-align: center;">Thao Tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listproduct}" var="prod">
                    <tr>
                        <td style="text-align: center; font-weight: 700; color: #94a3b8;">#${prod.productId}</td>
                        <td style="text-align: center;">
                            <c:choose>
                                <c:when test="${prod.images != null && prod.images.startsWith('http')}">
                                    <c:url value="${prod.images}" var="prodImgUrl" />
                                </c:when>
                                <c:otherwise>
                                    <c:url value="/image?fname=${prod.images}" var="prodImgUrl" />
                                </c:otherwise>
                            </c:choose>
                            <img class="img-preview-thumb" src="${prodImgUrl}" alt="${prod.productName}" 
                                 onerror="this.onerror=null;this.src='https://placehold.co/72x72?text=SP';" />
                        </td>
                        <td>
                            <span style="font-weight: 700; color: #0f172a; font-size: 14px;">${prod.productName}</span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${prod.category != null}">
                                    <span class="badge-category">${prod.category.categoryname}</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: #94a3b8; font-size: 12px; font-style: italic;">Chưa gán</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="product-price-cell">
                            <fmt:formatNumber value="${prod.price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </td>
                        <td style="text-align: center; font-weight: 700; font-size: 15px;">
                            ${prod.quantity}
                        </td>
                        <td style="text-align: center;">
                            <c:if test="${prod.status == 1}">
                                <span class="badge-status-active">Đang bán</span>
                            </c:if>
                            <c:if test="${prod.status != 1}">
                                <span class="badge-status-locked">Ngừng bán</span>
                            </c:if>
                        </td>
                        <td style="text-align: center;">
                            <a href="<c:url value='/admin/product/edit?id=${prod.productId}'/>" class="action-link action-edit">✏️ Sửa</a>
                            <a href="<c:url value='/admin/product/delete?id=${prod.productId}'/>" 
                               class="action-link action-delete" 
                               onclick="return confirm('Bạn có chắc muốn xóa sản phẩm [${prod.productName}]?');">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listproduct}">
                    <tr>
                        <td colspan="8">
                            <div class="empty-state">
                                <span class="empty-state-icon">📦</span>
                                <div class="empty-state-title">Chưa có sản phẩm nào</div>
                                <div class="empty-state-text">Bấm <strong>+ Thêm Sản Phẩm Mới</strong> để bắt đầu tạo dữ liệu.</div>
                            </div>
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
