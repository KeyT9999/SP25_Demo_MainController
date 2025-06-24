<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.NumberFormat, java.util.Locale" %>
<%
    String message = (String) request.getAttribute("message");
    String fullname = (String) request.getAttribute("fullname");
    java.math.BigDecimal total = (java.math.BigDecimal) request.getAttribute("total");
    String totalStr = total != null ? NumberFormat.getInstance(new Locale("vi", "VN")).format(total) : "0";
    String totalForQR = total != null ? total.toPlainString() : "0";
%>
<html>
    <head>
        <title>Thanh toán đơn hàng</title>
        <style>
            body {
                font-family: Arial,sans-serif;
                text-align: center;
                margin-top:60px;
            }
            .qr-box {
                display:inline-block;
                background:#f8fff6;
                border-radius:12px;
                box-shadow:0 0 8px #c7e8c4;
                padding: 38px 50px;
            }
            .qr-box img {
                width:180px;
                border-radius:12px;
                border:1.5px solid #2eb872;
            }
            .qr-box p {
                font-size:16px;
            }
            button {
                background: #2eb872;
                color:#fff;
                border:none;
                padding:10px 28px;
                border-radius:7px;
                font-size:17px;
                margin-top:18px;
            }
            .success {
                color: #219653;
                font-size: 18px;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <div class="qr-box">
            <% if (message != null && !"Đặt hàng thất bại hoặc không đủ tồn kho!".equals(message)) { %>
            <div class="success"><%= message %></div>
            <% } %>
            <h2>Quét mã QR để thanh toán</h2>
            <img src="https://img.vietqr.io/image/VCB-0123456789-compact.png?amount=<%= totalForQR %>&addInfo=Thanh%20toan%20ShopKeyT" alt="QR thanh toán Momo hoặc VietQR"/>
            <p><b>Ngân hàng:</b> MB BANK - 020720049999 - Chủ TK: TRAN KIM THANG</p>
            <p><b>Khách hàng:</b> <%= fullname != null ? fullname : "N/A" %></p>
            <p><b>Tổng tiền cần thanh toán:</b> <%= totalStr %> VNĐ</p>
            <p>Vui lòng chuyển khoản đúng số tiền và ghi rõ tên/sđt trong nội dung!</p>
            <form action="<%=request.getContextPath()%>/success" method="post">
                <button type="submit">Tôi đã chuyển khoản</button>
            </form>

        </div>
    </body>
</html>
