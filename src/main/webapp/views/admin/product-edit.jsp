<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="card-form-container">
    <div class="card-form-header">
        <h2>✏️ Chỉnh Sửa Sản Phẩm</h2>
    </div>
    
    <div class="card-form-body">
        <c:if test="${not empty alert}">
            <div class="admin-alert-danger">⚠️ ${alert}</div>
        </c:if>

        <form action="<c:url value='/admin/product/update'/>" method="post" enctype="multipart/form-data">
            <input type="hidden" name="productId" value="${product.productId}" />

            <div class="form-group">
                <label class="form-label" for="productName">Tên Sản Phẩm (*)</label>
                <input type="text" id="productName" name="productName" value="${product.productName}" 
                       class="form-control-input" required />
            </div>

            <div class="form-group">
                <label class="form-label" for="categoryId">Danh Mục Sản Phẩm (*)</label>
                <select id="categoryId" name="categoryId" class="form-control-input" required>
                    <c:forEach items="${categories}" var="cat">
                        <option value="${cat.categoryid}" ${product.category != null && product.category.categoryid == cat.categoryid ? 'selected' : ''}>
                            ${cat.categoryname}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="price">Giá Bán (VNĐ) (*)</label>
                    <input type="number" id="price" name="price" 
                           value="<fmt:formatNumber value='${product.price}' groupingUsed='false' maxFractionDigits='0'/>"
                           min="0" step="1000" class="form-control-input" required />
                </div>

                <div class="form-group">
                    <label class="form-label" for="quantity">Số Lượng Trong Kho (*)</label>
                    <input type="number" id="quantity" name="quantity" value="${product.quantity}" 
                           min="0" class="form-control-input" required />
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="description">Mô Tả Sản Phẩm</label>
                <textarea id="description" name="description" rows="4" class="form-control-input">${product.description}</textarea>
            </div>

            <div class="form-group">
                <label class="form-label">Hình Ảnh Hiện Tại</label>
                <c:choose>
                    <c:when test="${product.images != null && product.images.startsWith('http')}">
                        <c:url value="${product.images}" var="prodCurrentImg" />
                    </c:when>
                    <c:otherwise>
                        <c:url value="/image?fname=${product.images}" var="prodCurrentImg" />
                    </c:otherwise>
                </c:choose>
                <img style="width: 130px; height: 130px; object-fit: cover; border-radius: 10px; border: 2px solid #e2e8f0; display: block; margin-top: 8px;" 
                     src="${prodCurrentImg}" alt="${product.productName}" 
                     onerror="this.onerror=null;this.src='https://placehold.co/130x130?text=Product';" />
            </div>

            <div class="form-group">
                <label class="form-label" for="images">Đường Dẫn Hình Ảnh Mới (URL)</label>
                <input type="text" id="images" name="images" value="${product.images}" 
                       class="form-control-input" placeholder="https://images.unsplash.com/..." />
            </div>

            <div class="form-group">
                <label class="form-label" for="imageFile">Hoặc Thay Ảnh Từ Máy Tính</label>
                <input type="file" id="imageFile" name="imageFile" class="form-control-input" accept="image/*" />
            </div>

            <div class="form-group">
                <label class="form-label">Trạng Thái Bán Hàng</label>
                <div class="radio-inline">
                    <label class="radio-label">
                        <input type="radio" name="status" value="1" ${product.status == 1 ? 'checked' : ''} /> ✅ Đang Bán
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="status" value="0" ${product.status != 1 ? 'checked' : ''} /> ⛔ Ngừng Bán
                    </label>
                </div>
            </div>

            <div style="margin-top: 30px; display: flex; align-items: center;">
                <button type="submit" class="btn-submit-form" style="background: linear-gradient(135deg, #d97706, #b45309);">
                    💾 Cập Nhật Sản Phẩm
                </button>
                <a href="<c:url value='/admin/products'/>" class="btn-cancel-form">↩ Hủy Bỏ</a>
            </div>
        </form>
    </div>
</div>
