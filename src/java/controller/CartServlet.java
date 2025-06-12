package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Collection;
import jakarta.servlet.RequestDispatcher;
import model.Cart;
import model.CartItem;
import model.Product;
import service.ICartService;
import service.CartServiceImpl;
import service.IProductService;
import service.ProductServiceImpl;

@WebServlet(name = "CartServlet", urlPatterns = {"/carts"})
public class CartServlet extends HttpServlet {

    private IProductService productService;
    private ICartService cartService;

    @Override
    public void init() {
        productService = new ProductServiceImpl(); // Đúng tên class
        cartService = new CartServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        // Trả list CartItem cho JSP
        Collection<CartItem> cartItems = cart.getItems().values();
        request.setAttribute("cart", cartItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart/cart.jsp");
        dispatcher.forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "add";
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = 1;
        if (request.getParameter("quantity") != null) {
            quantity = Integer.parseInt(request.getParameter("quantity"));
        }

        Product product = null;
        try {
            product = productService.getProductById(productId);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý thêm nếu muốn báo lỗi, hoặc redirect về trang báo lỗi
        }

        switch (action) {
            case "add":
                if (product != null) {
                    cartService.addToCart(cart, product, quantity);
                }
                break;
            case "update":
                cartService.updateCartItem(cart, productId, quantity);
                break;
            case "remove":
                cartService.removeCartItem(cart, productId);
                break;
        }

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/carts");
    }

}
