<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Nhập thông tin nhận hàng</title>
    <style>
        body { font-family: Arial,sans-serif; text-align:center; margin-top:60px;}
        .form-box { background: #f1fff0; display:inline-block; border-radius:10px; box-shadow: 0 0 8px #bff7c5; padding:40px 50px;}
        input { margin: 8px 0; width: 90%; padding: 8px; border: 1px solid #b6e4cb; border-radius: 5px;}
        label { font-weight:bold; display:block; margin-bottom:2px;}
        button { background: #2eb872; color:#fff; border:none; padding:10px 30px; border-radius:6px; font-size: 16px; margin-top:14px;}
    </style>
</head>
<body>
    <div class="form-box">
        <h2>Nhập thông tin nhận hàng</h2>
        <form action="confirm-order" method="post">
            <label>Họ tên người nhận:</label>
            <input type="text" name="fullname" required>
            <label>Số điện thoại:</label>
            <input type="text" name="phone" required pattern="[0-9]{9,12}">
            <label>Địa chỉ nhận hàng:</label>
            <input type="text" name="address" required>
            <label>Ghi chú:</label>
            <input type="text" name="note" placeholder="Không bắt buộc">
            <button type="submit">Xác nhận thông tin</button>
        </form>
    </div>
</body>
</html>
