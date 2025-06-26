package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Product;
import service.IProductService;
import service.ProductServiceImpl;
import dao.jpa.ProductRepository;

import java.io.IOException;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

@WebServlet(name = "ProductListServlet", urlPatterns = "/productList")
public class ProductListServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        try {
            ProductRepository repo = new ProductRepository(em);
            ProductServiceImpl service = new ProductServiceImpl(repo);
            List<Product> products = service.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/cart/productListCart.jsp").forward(request, response);
        } catch (PersistenceException e) {
            e.printStackTrace();
            response.sendError(500, "Lỗi truy cập dữ liệu!");
        } finally {
            em.close();
        }
    }
} 