package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.User;
import userDao.UserDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    /* ------ SHOW LOGIN FORM (GET) ------ */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* Nếu đã đăng nhập rồi → về đúng trang quyền */
        HttpSession sess = req.getSession(false);
        if (sess != null && sess.getAttribute("user") != null) {
            User user = (User) sess.getAttribute("user");
            if ("admin".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/userList.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/productList.jsp");
            }
            return;
        }

        /* Forward tới login.jsp */
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    /* ------ HANDLE LOGIN (POST) ------ */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email     = req.getParameter("email");
        String password  = req.getParameter("password");
        boolean remember = req.getParameter("remember") != null;

        try {
            User user = userDao.checkLogin(email, password);   // hàm xác thực
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Invalid+credentials");
                return;
            }

            /* ---- Đăng nhập thành công ---- */
            HttpSession session = req.getSession();
            session.setAttribute("user", user); // Dùng key "user" nhé

            /* ---- Cookie Remember Me ---- */
            Cookie ck = new Cookie("rememberEmail", remember ? email : "");
            ck.setMaxAge(remember ? 7 * 24 * 60 * 60 : 0);   // 7 ngày hoặc xoá
            ck.setHttpOnly(true);
            ck.setPath(req.getContextPath());
            resp.addCookie(ck);

            /* Chuyển trang đúng theo quyền */
            if ("admin".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/userList.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/product/productList.jsp");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
