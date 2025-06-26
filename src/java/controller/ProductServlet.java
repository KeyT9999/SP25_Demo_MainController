package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Product;
import service.IProductService;
import service.ProductServiceImpl;
import dao.jpa.ProductRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        String action = req.getParameter("action");
        if (action == null) action = "";
        EntityManager em = emf.createEntityManager();
        try {
            ProductRepository repo = new ProductRepository(em);
            ProductServiceImpl service = new ProductServiceImpl(repo);
            switch (action) {
                case "create" -> showNewForm(req, resp);
                case "edit" -> showEditForm(req, resp, service);
                case "delete" -> deleteProduct(req, resp, service);
                case "confirmDelete" -> showDeleteForm(req, resp, service);
                default -> listProduct(req, resp, service);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            resp.sendError(500, "Lỗi truy cập dữ liệu!");
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        String action = req.getParameter("action");
        if (action == null) action = "";
        EntityManager em = emf.createEntityManager();
        try {
            ProductRepository repo = new ProductRepository(em);
            ProductServiceImpl service = new ProductServiceImpl(repo);
            switch (action) {
                case "create" -> insertProduct(req, resp, service);
                case "edit" -> updateProduct(req, resp, service);
                default -> resp.sendRedirect(req.getContextPath() + "/products");
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            resp.sendError(500, "Lỗi truy cập dữ liệu!");
        } finally {
            em.close();
        }
    }

    private void listProduct(HttpServletRequest req, HttpServletResponse resp, IProductService productService)
            throws ServletException, IOException {
        int pageSize = 10;
        int pageNo = 1;
        try {
            String p = req.getParameter("page");
            if (p != null) pageNo = Integer.parseInt(p);
            if (pageNo < 1) pageNo = 1;
        } catch (NumberFormatException ignore) {}
        int offset = (pageNo - 1) * pageSize;
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
        RequestDispatcher rd = req.getRequestDispatcher("/productList.jsp");
        rd.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/product/createProduct.jsp");
        rd.forward(req, resp);
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp, IProductService productService)
            throws IOException {
        Product p = buildProductFromRequest(req, 0, productService);
        productService.addProduct(p);
        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp, IProductService productService)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product p = productService.getProductById(id);
        if (p == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.setAttribute("product", p);
        RequestDispatcher rd = req.getRequestDispatcher("/product/editProduct.jsp");
        rd.forward(req, resp);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp, IProductService productService)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product p = buildProductFromRequest(req, id, productService);
        productService.modifyProduct(p);
        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp, IProductService productService)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productService.removeProduct(id);
        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp, IProductService productService)
            throws ServletException, IOException {
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

    private Product buildProductFromRequest(HttpServletRequest req, int id, IProductService productService) {
        String name = req.getParameter("name");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        String desc = req.getParameter("description");
        int stock = Integer.parseInt(req.getParameter("stock"));
        String rawDate = req.getParameter("importDate");
        LocalDateTime importDate = null;
        if (rawDate != null && !rawDate.isBlank()) {
            importDate = LocalDateTime.parse(rawDate);
        } else if (id != 0) {
            importDate = productService.getProductById(id).getImportDate();
        }
        boolean status = req.getParameter("status") != null;
        return (id == 0)
                ? new Product(name, price, desc, stock, importDate, status)
                : new Product(id, name, price, desc, stock, importDate, status);
    }
}
