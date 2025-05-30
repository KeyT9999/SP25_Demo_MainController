<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>

    <!-- NEW: tên người đăng nhập -->
    <p style="margin:4px 0;font-weight:500">
        Logged in as:
        <span style="color:#4f46e5">
            <c:out value="${sessionScope.currentUser.username}" />
        </span>
    </p>

    <h2>
        <a href="${pageContext.request.contextPath}/users?action=create">Add New User</a> |
        <a href="${pageContext.request.contextPath}/login.jsp?logout=1"
           style="color:#e11d48;font-weight:600">Logout</a>
    </h2>
</center>

<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>ID</th><th>Username</th><th>Email</th><th>Country</th>
            <th>Role</th><th>D.O.B</th><th>Status</th><th>Actions</th>
        </tr>

        <c:forEach var="user" items="${listUser}">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.username}" /></td>
                <td><c:out value="${user.email}" /></td>
                <td><c:out value="${user.country}" /></td>
                <td><c:out value="${user.role}" /></td>
                <td><c:out value="${user.dob}" /></td>
                <td>
                    <c:choose>
                        <c:when test="${user.status}">Active</c:when>
                        <c:otherwise>Inactive</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/users?action=delete&id=${user.id}"
                       onclick="return confirm('Delete this user?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
