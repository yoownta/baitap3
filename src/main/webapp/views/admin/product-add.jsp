<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="card-form-container">
    <div class="card-form-header">
        <h2>➕ Thêm Sản Phẩm Mới</h2>
    </div>
    
    <div class="card-form-body">
        <c:if test="${not empty alert}">
            <div class="admin-alert-danger">⚠️ ${alert}</div>
        </c:if>

        <form action="<c:url value='/admin/product/insert'/>" method="post" enctype="multipart/form-data">
            
            <div class="form-group">
                <label class="form-label" for="productName">Tên Sản Phẩm (*)</label>
                <input type="text" id="productName" name="productName" class="form-control-input" 
                       required placeholder="Nhập tên sản phẩm thời trang..." 
                       value="${not empty productName ? productName : ''}" />
            </div>

            <div class="form-group">
                <label class="form-label" for="categoryId">Danh Mục Sản Phẩm (*)</label>
                <select id="categoryId" name="categoryId" class="form-control-input" required>
                    <option value="">-- Chọn danh mục --</option>
                    <c:forEach items="${categories}" var="cat">
                        <option value="${cat.categoryid}">${cat.categoryname}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="price">Giá Bán (VNĐ) (*)</label>
                    <input type="number" id="price" name="price" min="0" step="1000" 
                           class="form-control-input" required placeholder="Ví dụ: 250000" />
                </div>

                <div class="form-group">
                    <label class="form-label" for="quantity">Số Lượng Trong Kho (*)</label>
                    <input type="number" id="quantity" name="quantity" min="0" 
                           class="form-control-input" required placeholder="Ví dụ: 50" />
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="description">Mô Tả Sản Phẩm</label>
                <textarea id="description" name="description" rows="4" class="form-control-input" 
                          placeholder="Thông tin chi tiết: chất liệu, form dáng, cách bảo quản..."></textarea>
            </div>

            <div class="form-group">
                <label class="form-label" for="images">Đường Dẫn Hình Ảnh (URL)</label>
                <input type="text" id="images" name="images" class="form-control-input" 
                       placeholder="https://images.unsplash.com/photo-..." />
            </div>

            <div class="form-group">
                <label class="form-label" for="imageFile">Hoặc Tải Ảnh Lên Từ Máy Tính</label>
                <input type="file" id="imageFile" name="imageFile" class="form-control-input" accept="image/*" />
            </div>

            <div class="form-group">
                <label class="form-label">Trạng Thái Bán Hàng</label>
                <div class="radio-inline">
                    <label class="radio-label">
                        <input type="radio" name="status" value="1" checked /> ✅ Đang Bán
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="status" value="0" /> ⛔ Ngừng Bán
                    </label>
                </div>
            </div>

            <div style="margin-top: 30px; display: flex; align-items: center;">
                <button type="submit" class="btn-submit-form">💾 Lưu Sản Phẩm</button>
                <a href="<c:url value='/admin/products'/>" class="btn-cancel-form">↩ Hủy Bỏ</a>
            </div>
        </form>
    </div>
</div>
