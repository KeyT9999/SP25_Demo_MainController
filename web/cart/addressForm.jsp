<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Nhập thông tin nhận hàng</title>
        <meta charset="UTF-8">
        <style>
            body {
                background: #f4f7fa;
                font-family: 'Segoe UI', 'Roboto', Arial, sans-serif;
                margin: 0;
                padding: 0;
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .form-box {
                background: #fff;
                border-radius: 18px;
                box-shadow: 0 4px 24px rgba(60, 95, 184, 0.10), 0 1.5px 7px rgba(0,0,0,0.08);
                padding: 32px 28px 24px 28px;
                max-width: 400px;
                width: 100%;
                margin: 24px;
            }
            .form-box h2 {
                text-align: center;
                margin-bottom: 22px;
                font-size: 1.45rem;
                font-weight: 700;
                color: #29549b;
                letter-spacing: 1px;
            }
            form {
                display: flex;
                flex-direction: column;
                gap: 16px;
            }
            label {
                font-weight: 500;
                margin-bottom: 5px;
                color: #2d3a4a;
            }
            input[type="text"],
            input[type="email"] {
                padding: 11px 14px;
                border: 1.5px solid #e2e6ea;
                border-radius: 7px;
                background: #f7fafd;
                font-size: 1rem;
                transition: border 0.2s, box-shadow 0.2s;
                margin-bottom: 4px;
            }
            input[type="text"]:focus,
            input[type="email"]:focus {
                border-color: #397eff;
                outline: none;
                box-shadow: 0 0 0 2px rgba(57, 126, 255, 0.08);
                background: #fff;
            }
            button[type="submit"] {
                margin-top: 10px;
                background: linear-gradient(90deg, #397eff, #57c6ff);
                color: #fff;
                font-weight: bold;
                border: none;
                border-radius: 8px;
                padding: 13px 0;
                font-size: 1.08rem;
                letter-spacing: 0.5px;
                cursor: pointer;
                box-shadow: 0 2px 8px rgba(33, 92, 255, 0.11);
                transition: background 0.15s, transform 0.12s;
            }
            button[type="submit"]:hover,
            button[type="submit"]:focus {
                background: linear-gradient(90deg, #29549b, #37a6ff);
                transform: translateY(-2px) scale(1.03);
            }
            @media (max-width: 500px) {
                .form-box {
                    padding: 22px 10px;
                    max-width: 98vw;
                }
                .form-box h2 {
                    font-size: 1.12rem;
                }
            }
        </style>

    </head>
    <body>
        <div class="form-box">
            <h2>Nhập thông tin nhận hàng</h2>

            <!-- LUÔN gắn contextPath cho chắc cú -->
            <form action="<%= request.getContextPath() %>/confirm-order" method="post">

                <label>Họ tên người nhận:</label>
                <input type="text" name="fullname" required>

                <label>Số điện thoại:</label>
                <input type="text" name="phone" required pattern="[0-9]{9,12}">

                <label>Email nhận xác nhận:</label>
                <input type="email" name="email" required>

                <label>Địa chỉ nhận hàng:</label>
                <input type="text" name="address" required>

                <label>Ghi chú:</label>
                <input type="text" name="note" placeholder="Không bắt buộc">

                <button type="submit">Xác nhận thông tin</button>
            </form>
        </div>
    </body>
</html>
