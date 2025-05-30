<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management • Add User</title>

    <style>
        /* ----- RESET & COLORS ----- */
        *{box-sizing:border-box;font-family:Segoe UI,Roboto,Helvetica,Arial,sans-serif}
        body{margin:0;background:#f4f6f9;color:#333;display:flex;flex-direction:column;min-height:100vh}

        /* ----- HEADER ----- */
        header{background:#4f46e5;color:#fff;padding:24px 0;text-align:center;box-shadow:0 2px 8px rgba(0,0,0,.15)}
        header h1{margin:0;font-size:28px;font-weight:600}
        header a{color:#ffd;opacity:.9;text-decoration:none;font-size:15px}
        header a:hover{opacity:1;text-decoration:underline}

        /* ----- CARD ----- */
        .card{width:380px;background:#fff;margin:48px auto;padding:32px 40px;border-radius:12px;box-shadow:0 4px 12px rgba(0,0,0,.1)}
        .card h2{margin:0 0 24px;text-align:center;font-size:22px;color:#4f46e5}

        /* ----- FORM ELEMENTS ----- */
        label{display:block;font-weight:500;margin-top:14px;margin-bottom:6px}
        input[type=text],input[type=email],input[type=password],input[type=date],select{
            width:100%;padding:10px 12px;border:1px solid #ccc;border-radius:6px;font-size:14px}
        input:focus,select:focus{border-color:#4f46e5;outline:0;box-shadow:0 0 0 2px rgba(79,70,229,.15)}

        .chk{display:flex;align-items:center;margin-top:14px}
        .chk input{margin-right:8px;transform:scale(1.1)}

        .btn{width:100%;margin-top:26px;padding:12px;border:none;border-radius:6px;background:#4f46e5;color:#fff;font-size:15px;font-weight:600;cursor:pointer}
        .btn:hover{background:#4338ca}

        footer{margin-top:auto;padding:12px;text-align:center;font-size:13px;color:#777}
    </style>
</head>
<body>

<header>
    <h1>User Management</h1>
    <a href="${pageContext.request.contextPath}/users">← Back to List</a>
</header>

<section class="card">
    <h2>Add New User</h2>

    <form action="${pageContext.request.contextPath}/users?action=create" method="post">

        <label>Username</label>
        <input type="text" name="username" required/>

        <label>Email</label>
        <input type="email" name="email" required/>

        <label>Country</label>
        <input type="text" name="country"/>

        <label>Role</label>
        <select name="role">
            <option value="user">User</option>
            <option value="admin">Admin</option>
        </select>

        <label>Password</label>
        <input type="password" name="password" required/>

        <label>Date of Birth</label>
        <input type="date" name="dob" />

        <div class="chk">
            <input type="checkbox" name="status" checked>
            <span>Status (Active)</span>
        </div>

        <button class="btn" type="submit">Save User</button>
    </form>
</section>

<footer>© 2025 Demo Project</footer>
</body>
</html>
