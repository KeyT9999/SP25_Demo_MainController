package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Product;
import service.IProductService;
import service.ProductServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IProductService productService;

    @Override
    public void init() {
        productService = new ProductServiceImpl();
    }

    /* ========================================================= */
 /*                          ROUTER                           */
 /* ========================================================= */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* ----- GUARD (must login) ----- */
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create" ->
                    showNewForm(req, resp);
                case "edit" ->
                    showEditForm(req, resp);
                case "delete" ->
                    deleteProduct(req, resp);
                case "confirmDelete" ->
                    showDeleteForm(req, resp);
                default ->
                    listProduct(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* guard tương tự */
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create" ->
                    insertProduct(req, resp);
                case "edit" ->
                    updateProduct(req, resp);
                default ->
                    resp.sendRedirect(req.getContextPath() + "/products");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listProduct(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {

        /* Đọc tham số page; mặc định 1 */
        int pageSize = 10;
        int pageNo = 1;
        try {
            String p = req.getParameter("page");
            if (p != null) {
                pageNo = Integer.parseInt(p);
            }
            if (pageNo < 1) {
                pageNo = 1;
            }
        } catch (NumberFormatException ignore) {
        }

        int offset = (pageNo - 1) * pageSize;

        /* Nếu ProductService chưa có paging, tạm lấy toàn bộ rồi cắt */
        List<Product> all = productService.getAllProducts();
        int totalRows = all.size();
        int totalPages = (int) Math.ceil(totalRows / (double) pageSize);

        List<Product> pageData = all.stream()
                .skip(offset)
                .limit(pageSize)
                .toList();

        req.setAttribute("products", pageData);
        req.setAttribute("currentPage", pageNo);
        req.setAttribute("totalPages", totalPages);

        RequestDispatcher rd = req.getRequestDispatcher("/product/productList.jsp");
        rd.forward(req, resp);
    }

    /* ========================================================= */
 /*                          CREATE                            */
 /* ========================================================= */
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/product/createProduct.jsp");
        rd.forward(req, resp);
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        Product p = buildProductFromRequest(req, 0);
        productService.addProduct(p);

        resp.sendRedirect(req.getContextPath() + "/products");
    }

    /* ========================================================= */
 /*                          UPDATE                            */
 /* ========================================================= */
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {

        int id = Integer.parseInt(req.getParameter("id"));
        Product p = productService.getProductById(id);
        if (p == null) {                       // không tồn tại
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.setAttribute("product", p);
        RequestDispatcher rd = req.getRequestDispatcher("/product/editProduct.jsp");
        rd.forward(req, resp);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Product p = buildProductFromRequest(req, id);
        productService.modifyProduct(p);

        resp.sendRedirect(req.getContextPath() + "/products");
    }

    /* ========================================================= */
 /*                          DELETE                            */
 /* ========================================================= */
    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        productService.removeProduct(id);

        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product p = productService.getProductById(id);
        if (p == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.setAttribute("product", p);
        RequestDispatcher rd = req.getRequestDispatcher("/product/deleteProduct.jsp");
        rd.forward(req, resp);
    }

    /* ========================================================= */
 /*                       HELPER METHOD                        */
 /* ========================================================= */
    /**
     * Build Product object from request parameters
     */
    private Product buildProductFromRequest(HttpServletRequest req, int id)
            throws SQLException {

        String name = req.getParameter("name");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        String desc = req.getParameter("description");
        int stock = Integer.parseInt(req.getParameter("stock"));

        /* ----- importDate null-safe ----- */
        String rawDate = req.getParameter("importDate");
        LocalDateTime importDate = null;
        if (rawDate != null && !rawDate.isBlank()) {
            importDate = LocalDateTime.parse(rawDate);           // ISO yyyy-MM-ddTHH:mm
        } else if (id != 0) {                                    // update và user để trống
            importDate = productService.getProductById(id).getImportDate();
        }

        boolean status = req.getParameter("status") != null;

        return (id == 0)
                ? new Product(name, price, desc, stock, importDate, status) // insert
                : new Product(id, name, price, desc, stock, importDate, status); // update
    }

}
