<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Không đủ quyền truy cập</title>
    <style>
        body {
            background: #f5f6fa;
            color: #333;
            font-family: 'Segoe UI', Roboto, Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .card {
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 4px 18px rgba(79,70,229,.12);
            padding: 36px 48px;
            text-align: center;
        }
        h2 {
            color: #e11d48;
            font-size: 26px;
            margin-bottom: 12px;
        }
        .emoji {
            font-size: 44px;
            margin-bottom: 20px;
            display: block;
        }
        a {
            display: inline-block;
            margin-top: 22px;
            color: #4f46e5;
            font-weight: 600;
            text-decoration: none;
            border-bottom: 1.5px dashed #4f46e5;
        }
        a:hover { color: #e11d48; border-bottom: 1.5px solid #e11d48; }
    </style>
</head>
<body>
    <div class="card">
        <span class="emoji">🚫👑</span>
        <h2>Xin lỗi Công Chúa,<br>bạn không đủ quyền truy cập trang này!</h2>
        <div>
            Vui lòng quay về trang phù hợp hoặc liên hệ quản trị nếu bạn nghĩ đây là nhầm lẫn.
        </div>
        <a href="productList.jsp">🛒 Quay về danh sách sản phẩm</a>
    </div>
</body>
</html>
