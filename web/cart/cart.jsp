<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Giỏ hàng</title>
        <meta charset="UTF-8">
        <style>
            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                background: #f7f8fa;
                text-align: center;
                margin: 0;
                padding: 0;
            }
            h2 {
                color: #2eb872;
                font-size: 2em;
                margin: 40px 0 28px 0;
                letter-spacing: 1px;
            }
            .cart-table {
                margin: 0 auto;
                border-collapse: separate;
                border-spacing: 0;
                width: 85%;
                background: #fff;
                box-shadow: 0 3px 24px #2eb87216;
                border-radius: 18px;
                overflow: hidden;
            }
            .cart-table th, .cart-table td {
                padding: 16px 9px;
                font-size: 1.13em;
            }
            .cart-table th {
                background: #e6f9f1;
                color: #199d72;
                font-weight: bold;
            }
            .cart-table tr:nth-child(even) td {
                background: #f7fcfa;
            }
            .cart-table td {
                color: #222;
                border-bottom: 1.5px solid #e5efe6;
            }
            .cart-table tr:last-child td {
                border-bottom: none;
            }
            .cart-table td:last-child, .cart-table th:last-child {
                color: #e64a19;
                font-weight: 600;
            }
            .qty-btn {
                background: #2eb872;
                color: #fff;
                border: none;
                border-radius: 4px;
                font-size: 1.15em;
                width: 30px;
                height: 30px;
                cursor: pointer;
                margin: 0 4px;
                font-weight: bold;
                transition: background 0.14s;
            }
            .qty-btn:disabled {
                background: #ddd !important;
                color: #aaa !important;
                cursor: not-allowed !important;
            }
            .empty-cart {
                margin: 50px 0;
                color: #888;
                font-size: 1.22em;
            }
            .checkout-btn {
                background: linear-gradient(90deg, #2eb872 70%, #1ac7a6 100%);
                color: white;
                padding: 15px 54px;
                border: none;
                border-radius: 12px;
                font-size: 1.18em;
                font-weight: bold;
                box-shadow: 0 2px 10px #2eb87222;
                margin-top: 38px;
                margin-bottom: 22px;
                cursor: pointer;
                transition: background 0.16s, transform 0.10s;
            }
            .checkout-btn:hover {
                background: linear-gradient(90deg, #1ac7a6 60%, #2eb872 100%);
                transform: scale(1.04);
            }
            @media (max-width: 700px) {
                .cart-table th, .cart-table td {
                    padding: 10px 5px;
                    font-size: 1em;
                }
                .cart-table {
                    width: 99%;
                }
            }
        </style>
    </head>
    <body>
        <h2>🛒 Giỏ hàng của bạn</h2>

        <!-- THÔNG BÁO LỖI -->
        <c:if test="${not empty cartError}">
            <div style="color: #fff; background: #e64a19; padding: 14px 0; border-radius: 7px; width: 60%; margin: 0 auto 20px auto; font-weight: bold; box-shadow: 0 1px 6px #e64a1911;">
                ${cartError}
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty cart}">
                <div class="empty-cart">
                    Giỏ hàng trống!<br>
                    <a href="<%=request.getContextPath()%>/cart/productListCart.jsp" style="color:#1ac7a6;font-weight:bold;text-decoration:none;">⬅️ Tiếp tục mua sắm</a>
                </div>
            </c:when>
            <c:otherwise>
                <table class="cart-table">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cart}">
                            <tr>
                                <td>${item.product.name}</td>
                                <td style="color:#2eb872;font-weight:600;">
                                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </td>
                                <td>
                                    <form action="carts" method="post" style="display:inline;">
                                        <input type="hidden" name="productId" value="${item.product.id}" />
                                        <input type="hidden" name="action" value="decrease" />
                                        <button type="submit" class="qty-btn"
                                                <c:if test="${item.quantity == 1}">disabled</c:if>>-</button>
                                        </form>
                                        <input type="text" value="${item.quantity}" readonly
                                           style="width:28px;text-align:center;border:none;background:none;font-weight:bold;" />
                                    <form action="carts" method="post" style="display:inline;">
                                        <input type="hidden" name="productId" value="${item.product.id}" />
                                        <input type="hidden" name="action" value="increase" />
                                        <button type="submit" class="qty-btn"
                                                <c:if test="${item.quantity >= item.product.stock}">disabled</c:if>>+</button>
                                        </form>
                                        <br>
                                        <span style="font-size:0.95em;color:#888;">
                                            <b>Tồn kho:</b> ${item.product.stock}
                                    </span>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <!-- Tổng tiền toàn bộ giỏ hàng -->
                    <tfoot>
                        <tr>
                            <td colspan="3" style="text-align:right;font-weight:bold;">Tổng cộng:</td>
                            <td style="color:#e64a19;font-weight:bold;">
                                <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                            </td>
                        </tr>
                    </tfoot>
                </table>
                <form action="checkout" method="post">
                    <input type="submit" class="checkout-btn" value="Tiến hành thanh toán">
                </form>
            </c:otherwise>
        </c:choose>
    </body>
</html>
