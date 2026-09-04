<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="card-form-container">
    <div class="card-form-header">
        <h2>➕ Thêm Danh Mục Mới</h2>
    </div>
    
    <div class="card-form-body">
        <c:if test="${not empty alert}">
            <div class="admin-alert-danger">⚠️ ${alert}</div>
        </c:if>

        <form action="<c:url value='/admin/category/insert'/>" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label class="form-label" for="categoryname">Tên Danh Mục (*)</label>
                <input type="text" id="categoryname" name="categoryname" class="form-control-input" 
                       required placeholder="Nhập tên danh mục sản phẩm..." />
            </div>

            <div class="form-group">
                <label class="form-label" for="images">Đường Dẫn Hình Ảnh (URL)</label>
                <input type="text" id="images" name="images" class="form-control-input" 
                       placeholder="https://example.com/image.jpg" />
            </div>

            <div class="form-group">
                <label class="form-label" for="images1">Hoặc Tải Ảnh Từ Máy Tính</label>
                <input type="file" id="images1" name="images1" class="form-control-input" accept="image/*" />
            </div>

            <div class="form-group">
                <label class="form-label">Trạng Thái Hoạt Động</label>
                <div class="radio-inline">
                    <label class="radio-label">
                        <input type="radio" name="status" value="1" checked /> ✅ Hoạt Động
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="status" value="0" /> 🔒 Khóa
                    </label>
                </div>
            </div>

            <div style="margin-top: 30px; display: flex; align-items: center;">
                <button type="submit" class="btn-submit-form">💾 Lưu Danh Mục</button>
                <a href="<c:url value='/admin/categories'/>" class="btn-cancel-form">↩ Hủy Bỏ</a>
            </div>
        </form>
    </div>
</div>
