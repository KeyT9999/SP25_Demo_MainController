<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Đặt hàng thành công!</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 80px; }
        .success-box { display: inline-block; padding: 40px 60px; border-radius: 12px; background: #f1fff0; box-shadow: 0 0 10px #bff7c5; }
        h2 { color: #2eb872; }
        a { color: #2eb872; text-decoration: none; font-weight: bold; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="success-box">
        <h2>🎉 Đặt hàng thành công!</h2>
        <p>Cảm ơn bạn đã mua sắm tại Tạp Hóa KeyT.</p>
        <a href="<%= request.getContextPath() %>/cart/productListCart.jsp">⬅️ Quay về cửa hàng</a>
    </div>
</body>
</html>
