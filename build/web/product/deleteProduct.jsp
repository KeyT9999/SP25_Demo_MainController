<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Confirm delete</title>
        <style>
            *{
                box-sizing:border-box;
                font-family:Segoe UI,Roboto,Helvetica,Arial,sans-serif
            }
            body{
                margin:0;
                background:#f4f6f9;
                display:flex;
                align-items:center;
                justify-content:center;
                height:100vh
            }
            .box{
                background:#fff;
                padding:36px 48px;
                border-radius:12px;
                text-align:center;
                box-shadow:0 4px 12px rgba(0,0,0,.15);
                width:380px
            }
            h2{
                margin:0 0 16px;
                color:#e11d48
            }
            p{
                margin:0 0 24px
            }
            .btn{
                padding:10px 22px;
                border:none;
                border-radius:6px;
                font-weight:600;
                cursor:pointer
            }
            .danger{
                background:#e11d48;
                color:#fff
            }
            .secondary{
                background:#64748b;
                color:#fff;
                margin-left:8px
            }
            .btn:hover{
                opacity:.9
            }
        </style>
    </head>
    <body>
        <div class="box">
            <h2>Delete product?</h2>
            <p>
                Bạn chắc chắn muốn xoá <strong>${product.name}</strong>
                (ID: ${product.id}) khỏi danh sách?
            </p>

            <form action="${pageContext.request.contextPath}/products" method="post" style="display:inline">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="${product.id}"/>
                <button type="submit" class="btn danger">Yes, delete</button>
            </form>

            <a href="${pageContext.request.contextPath}/products" class="btn secondary">Cancel</a>
        </div>
    </body>
</html>
