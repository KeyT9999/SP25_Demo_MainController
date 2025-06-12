<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        </style>
    </head>
    <body>
        <div class="qr-box">
            <h2>Quét mã QR để thanh toán</h2>
            <img src="https://img.vietqr.io/image/VCB-0123456789-compact.png?amount=500000&addInfo=Thanh%20toan%20ShopKeyT" alt="QR thanh toán Momo hoặc VietQR"/>
            <p><b>Ngân hàng:</b> MB BANK - 020720049999 - Chủ TK: TRAN KIM THANG</p>
            <p>Vui lòng chuyển khoản đúng số tiền và ghi rõ tên/sđt trong nội dung!</p>
            <form action="cart/success.jsp" method="get">
                <button type="submit">Tôi đã chuyển khoản</button>
            </form>
        </div>
    </body>
</html>
