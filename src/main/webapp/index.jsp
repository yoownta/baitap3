<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    response.sendRedirect(request.getContextPath() + "/home");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SStore Fashion</title>
<meta http-equiv="refresh" content="0; url=<c:url value='/home'/>" />
</head>
<body>
    <p>Đang chuyển hướng đến trang chủ <a href="<c:url value='/home'/>">SStore Trang Chủ</a>...</p>
</body>
</html>
