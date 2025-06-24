<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product Management • List</title>
        <style>
            *{box-sizing:border-box;font-family:Segoe UI,Roboto,Helvetica,Arial,sans-serif}
            body{margin:0;background:#f4f6f9;color:#333;display:flex;flex-direction:column;min-height:100vh}
            header{background:#4f46e5;color:#fff;padding:24px 0;text-align:center;box-shadow:0 2px 8px rgba(0,0,0,.15)}
            header h1{margin:0;font-size:28px;font-weight:600}
            header p{margin:4px 0}
            header a{color:#ffd;opacity:.9;text-decoration:none;font-size:15px}
            header a:hover{opacity:1;text-decoration:underline}
            .card{width:90%;max-width:1000px;margin:40px auto;background:#fff;padding:24px 32px;
                  border-radius:12px;box-shadow:0 4px 12px rgba(0,0,0,.1)}
            .card h2{margin:0 0 18px;font-size:22px;color:#4f46e5}
            table{width:100%;border-collapse:collapse}
            th,td{padding:10px;border:1px solid #ddd;font-size:14px;text-align:center}
            th{background:#eef2ff;font-weight:600}
            tr:nth-child(even){background:#fafafa}
            .pager{text-align:center;margin-top:24px}
            .pager a{display:inline-block;margin:0 4px;padding:6px 12px;border-radius:6px;
                     background:#eef2ff;color:#4f46e5;text-decoration:none;font-size:14px}
            .pager a.active{background:#4f46e5;color:#fff;pointer-events:none}
            .pager a:hover{background:#c7d2fe}
            footer{margin-top:auto;padding:12px;text-align:center;font-size:13px;color:#777}
        </style>
    </head>
    <body>

        <header>
            <h1>Product Management</h1>
            <p>
                Logged in as:
                <span style="font-weight:600">
                    <c:out value="${sessionScope.user.username}" />
                </span>
            </p>
            <p>
                <a href="${pageContext.request.contextPath}/products?action=create">Add New Product</a> |
                <a href="${pageContext.request.contextPath}/login.jsp?logout=1"
                   style="color:#ffd;font-weight:600">Logout</a>
            </p>
        </header>

        <section class="card">
            <h2>List of Products</h2>
            <table>
                <tr>
                    <th>ID</th><th>Name</th><th>Price</th><th>Description</th>
                    <th>Stock</th><th>Import&nbsp;Date</th><th>Status</th><th>Actions</th>
                </tr>

                <c:choose>
                    <c:when test="${not empty products}">
                        <c:forEach var="p" items="${products}">
                            <tr>
                                <td>${p.id}</td>
                                <td>${p.name}</td>
                                <td>${p.price}</td>
                                <td>${p.description}</td>
                                <td>${p.stock}</td>
                                <td>
                                    <c:out value="${p.importDate}" />
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.status}">Active</c:when>
                                        <c:otherwise>Inactive</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/products?action=edit&id=${p.id}">Edit</a> |
                                    <a href="${pageContext.request.contextPath}/products?action=confirmDelete&id=${p.id}">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" style="color:#888">No products available.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>

            <!-- Pagination -->
            <div class="pager">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/products?page=${currentPage-1}">Prev</a>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a href="${pageContext.request.contextPath}/products?page=${i}"
                       class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/products?page=${currentPage+1}">Next</a>
                </c:if>
            </div>
        </section>

        <footer>© 2025 Demo Project</footer>
    </body>
</html>
