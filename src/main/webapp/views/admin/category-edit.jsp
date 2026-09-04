<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="card-form-container">
    <div class="card-form-header">
        <h2>✏️ Chỉnh Sửa Danh Mục</h2>
    </div>
    
    <div class="card-form-body">
        <c:if test="${not empty alert}">
            <div class="admin-alert-danger">⚠️ ${alert}</div>
        </c:if>

        <form action="<c:url value='/admin/category/update'/>" method="post" enctype="multipart/form-data">
            <input type="hidden" name="categoryid" value="${cate.categoryid}" />

            <div class="form-group">
                <label class="form-label" for="categoryname">Tên Danh Mục (*)</label>
                <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}" 
                       class="form-control-input" required />
            </div>

            <div class="form-group">
                <label class="form-label">Hình Ảnh Hiện Tại</label>
                <c:choose>
                    <c:when test="${cate.images != null && cate.images.startsWith('http')}">
                        <c:url value="${cate.images}" var="imgUrl" />
                    </c:when>
                    <c:otherwise>
                        <c:url value="/image?fname=${cate.images}" var="imgUrl" />
                    </c:otherwise>
                </c:choose>
                <img style="width: 130px; height: 90px; object-fit: cover; border-radius: 10px; border: 2px solid #e2e8f0; display: block; margin-top: 8px;"
                     src="${imgUrl}" alt="${cate.categoryname}" 
                     onerror="this.onerror=null;this.src='https://placehold.co/130x90?text=DM';" />
            </div>

            <div class="form-group">
                <label class="form-label" for="images">Đường Dẫn Hình Ảnh Mới (URL)</label>
                <input type="text" id="images" name="images" value="${cate.images}" 
                       class="form-control-input" placeholder="https://example.com/image.jpg" />
            </div>

            <div class="form-group">
                <label class="form-label" for="images1">Hoặc Thay Ảnh Mới Từ Máy Tính</label>
                <input type="file" id="images1" name="images1" class="form-control-input" accept="image/*" />
            </div>

            <div class="form-group">
                <label class="form-label">Trạng Thái Hoạt Động</label>
                <div class="radio-inline">
                    <label class="radio-label">
                        <input type="radio" name="status" value="1" ${cate.status == 1 ? 'checked' : ''} /> ✅ Hoạt Động
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="status" value="0" ${cate.status != 1 ? 'checked' : ''} /> 🔒 Khóa
                    </label>
                </div>
            </div>

            <div style="margin-top: 30px; display: flex; align-items: center;">
                <button type="submit" class="btn-submit-form" style="background: linear-gradient(135deg, #d97706, #b45309);">
                    💾 Cập Nhật Danh Mục
                </button>
                <a href="<c:url value='/admin/categories'/>" class="btn-cancel-form">↩ Hủy Bỏ</a>
            </div>
        </form>
    </div>
</div>
