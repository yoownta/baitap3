<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="card-panel">
    <div class="card-header">
        <h2>📁 Danh Sách Danh Mục Sản Phẩm</h2>
        <a href="<c:url value='/admin/category/add'/>" class="btn-create">+ Thêm Danh Mục Mới</a>
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
                    <th style="width: 60px; text-align: center;">STT</th>
                    <th style="width: 120px; text-align: center;">Hình Ảnh</th>
                    <th>Tên Danh Mục</th>
                    <th style="width: 150px; text-align: center;">Trạng Thái</th>
                    <th style="width: 160px; text-align: center;">Thao Tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listcate}" var="cate" varStatus="STT">
                    <tr>
                        <td style="text-align: center; font-weight: 700; color: #94a3b8;">${STT.index + 1}</td>
                        <td style="text-align: center;">
                            <c:choose>
                                <c:when test="${cate.images != null && cate.images.startsWith('http')}">
                                    <c:url value="${cate.images}" var="imgUrl" />
                                </c:when>
                                <c:otherwise>
                                    <c:url value="/image?fname=${cate.images}" var="imgUrl" />
                                </c:otherwise>
                            </c:choose>
                            <img class="img-preview-thumb" style="width: 80px; height: 60px;" 
                                 src="${imgUrl}" alt="${cate.categoryname}" 
                                 onerror="this.onerror=null;this.src='https://placehold.co/80x60?text=DM';" />
                        </td>
                        <td>
                            <span style="font-weight: 700; color: #0f172a; font-size: 15px;">${cate.categoryname}</span>
                        </td>
                        <td style="text-align: center;">
                            <c:if test="${cate.status == 1}">
                                <span class="badge-status-active">Hoạt Động</span>
                            </c:if>
                            <c:if test="${cate.status != 1}">
                                <span class="badge-status-locked">Bị Khóa</span>
                            </c:if>
                        </td>
                        <td style="text-align: center;">
                            <a href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>" 
                               class="action-link action-edit">✏️ Sửa</a>
                            <a href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>" 
                               class="action-link action-delete" 
                               onclick="return confirm('Bạn có chắc muốn xóa danh mục [${cate.categoryname}]?');">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listcate}">
                    <tr>
                        <td colspan="5">
                            <div class="empty-state">
                                <span class="empty-state-icon">📁</span>
                                <div class="empty-state-title">Chưa có danh mục nào</div>
                                <div class="empty-state-text">Bấm <strong>+ Thêm Danh Mục Mới</strong> để tạo dữ liệu.</div>
                            </div>
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
