<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="productDao.ProductDao" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="productDAO" class="productDao.ProductDao" scope="page"/>
<jsp:useBean id="products" class="java.util.ArrayList" scope="request"/>
<%
    request.setAttribute("products", productDAO.selectAllProducts());
%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh Sách Sản Phẩm</title>
        <style>
            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                background: #f7f8fa;
                margin: 0;
                padding: 0;
                text-align: center;
            }
            h2 {
                margin-top: 32px;
                color: #2eb872;
                font-size: 2.2em;
                letter-spacing: 1px;
            }
            .product-list {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 24px;
                margin: 32px 0;
            }
            .product {
                background: #fff;
                border-radius: 16px;
                box-shadow: 0 6px 32px rgba(60, 220, 180, 0.06), 0 2px 8px rgba(0,0,0,0.04);
                border: 1.5px solid #e6f2ec;
                padding: 22px 18px 24px 18px;
                width: 240px;
                min-height: 215px;
                display: flex;
                flex-direction: column;
                align-items: center;
                transition: transform 0.12s, box-shadow 0.12s;
                position: relative;
            }
            .product:hover {
                transform: translateY(-7px) scale(1.03);
                box-shadow: 0 14px 36px #7adcb244, 0 4px 18px #2eb87222;
                border-color: #29cf9d;
            }
            .product h3 {
                font-size: 1.16em;
                font-weight: bold;
                color: #222;
                margin: 0 0 8px 0;
                height: 36px;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
            }
            .product p {
                font-size: 1em;
                color: #2eb872;
                font-weight: 500;
                margin: 0 0 12px 0;
            }
            form {
                margin-top: auto;
                width: 100%;
            }
            input[type="number"] {
                width: 56px;
                padding: 6px;
                border-radius: 6px;
                border: 1px solid #cce6da;
                font-size: 1em;
                margin-right: 7px;
                outline: none;
            }
            button {
                background: linear-gradient(90deg, #2eb872 65%, #1ac7a6 100%);
                color: white;
                padding: 7px 17px;
                border: none;
                border-radius: 6px;
                font-size: 1em;
                cursor: pointer;
                font-weight: bold;
                box-shadow: 0 2px 10px #2eb87222;
                transition: background 0.18s, box-shadow 0.16s, transform 0.10s;
            }
            button:hover {
                background: linear-gradient(90deg, #1ac7a6 60%, #2eb872 100%);
                box-shadow: 0 4px 16px #7adcb244;
                transform: scale(1.04);
            }
            a.cart-link {
                display: inline-block;
                margin-top: 14px;
                margin-bottom: 12px;
                color: #138a5a;
                font-weight: bold;
                font-size: 1.13em;
                text-decoration: none;
                border-bottom: 2px solid #2eb87244;
                transition: color 0.15s, border-bottom 0.15s;
            }
            a.cart-link:hover {
                color: #0f6a44;
                border-bottom: 2px solid #2eb872;
            }
            /* Responsive */
            @media (max-width: 900px) {
                .product-list { gap: 16px; }
                .product { width: 43vw; min-width: 180px; }
            }
            @media (max-width: 600px) {
                .product-list { flex-direction: column; gap: 0;}
                .product { width: 94vw; min-width: 0; margin: 16px auto;}
            }
        </style>
    </head>
    <body>
        <h2>Danh Sách Sản Phẩm</h2>
        

        <div class="product-list">
            <c:forEach var="product" items="${products}">
                <div class="product">
                    <h3>${product.name}</h3>
                    <p>Giá: <span style="color:#ee4444; font-weight:bold">${product.price} VND</span></p>
                    <form action="<%= request.getContextPath() %>/carts" method="post">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="number" name="quantity" value="1" min="1">
                        <button type="submit">Thêm vào Giỏ</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </body>
</html>
