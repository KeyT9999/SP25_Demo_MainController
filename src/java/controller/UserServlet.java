package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;
import service.IUserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Điều phối CRUD cho entity User
 *   <url-pattern>/users</url-pattern>
 */
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl();     // <- gọi qua Service
    }

    /* ========== ROUTER ========== */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> showNewForm(req, resp);
                case "edit"   -> showEditForm(req, resp);
                case "delete" -> deleteUser(req, resp);
                default       -> listUser(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> insertUser(req, resp);
                case "edit"   -> updateUser(req, resp);
                default       -> resp.sendRedirect(req.getContextPath() + "/users");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /* ---------- CREATE ---------- */
    private void insertUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        String username = req.getParameter("username");
        String email    = req.getParameter("email");
        String country  = req.getParameter("country");
        String role     = req.getParameter("role");
        String password = req.getParameter("password");
        boolean status  = req.getParameter("status") != null;
        LocalDate dob   = parseDob(req.getParameter("dob"));

        User newUser = new User(username, email, country,
                                role, status, password, dob);

        userService.addUser(newUser);            // <- service

        resp.sendRedirect(req.getContextPath() + "/users");
    }

    /* ---------- UPDATE ---------- */
    private void updateUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int    id       = Integer.parseInt(req.getParameter("id"));
        String username = req.getParameter("username");
        String email    = req.getParameter("email");
        String country  = req.getParameter("country");
        String role     = req.getParameter("role");
        String password = req.getParameter("password");
        boolean status  = req.getParameter("status") != null;
        LocalDate dob   = parseDob(req.getParameter("dob"));

        User user = new User(id, username, email, country,
                             role, status, password, dob);

        userService.modifyUser(user);            // <- service

        resp.sendRedirect(req.getContextPath() + "/users");
    }

    /* ---------- READ (forms) ---------- */
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/user/createUser.jsp");
        rd.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {

        int  id   = Integer.parseInt(req.getParameter("id"));
        User user = userService.getUserById(id); // <- service

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/editUser.jsp");
        rd.forward(req, resp);
    }

    /* ---------- DELETE ---------- */
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        userService.removeUser(id);              // <- service

        resp.sendRedirect(req.getContextPath() + "/users");
    }

    /* ---------- LIST ---------- */
    private void listUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {

        List<User> users = userService.getAllUsers();   // <- service
        req.setAttribute("listUser", users);

        RequestDispatcher rd = req.getRequestDispatcher("/user/userList.jsp");
        rd.forward(req, resp);
    }

    /* ---------- Helper: parse LocalDate ---------- */
    private LocalDate parseDob(String dobParam) {
        try {
            return dobParam == null || dobParam.isBlank()
                   ? null
                   : LocalDate.parse(dobParam);
        } catch (Exception ex) {
            return null;
        }
    }
}
