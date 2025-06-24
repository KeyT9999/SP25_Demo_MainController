package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User; // Sửa đúng package model.User nếu khác

import java.io.IOException;

// Có thể filter tất cả hoặc chỉ filter các url admin
@WebFilter(urlPatterns = {"/productList.jsp", "/createProduct.jsp", "/editProduct.jsp", "/deleteProduct.jsp",
                          "/userList.jsp", "/createUser.jsp", "/editUser.jsp"})
public class AuthorizationFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Check login
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        String uri = req.getRequestURI();

        if (user == null) {
            // Chưa đăng nhập
            res.sendRedirect("login.jsp");
            return;
        }

        // Phân quyền
        String role = user.getRole(); // hoặc session.getAttribute("role")
        if (uri.contains("userList") || uri.contains("createUser") || uri.contains("editUser")
                || uri.contains("createProduct") || uri.contains("editProduct") || uri.contains("deleteProduct")) {
            // Các chức năng này chỉ cho admin
            if (!"admin".equalsIgnoreCase(role)) {
                res.sendRedirect("not_authorized.jsp"); // Tạo trang này để thông báo không đủ quyền
                return;
            }
        } else if (uri.contains("productList")) {
            // Trang productList.jsp, cả user và admin đều truy cập được
            // Không cần chặn
        }
        // Cho qua
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
