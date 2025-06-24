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
        productService = new ProductServiceImpl();
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

        // Lấy thông báo lỗi từ session (nếu có)
        String cartError = (String) session.getAttribute("cartError");
        if (cartError != null) {
            request.setAttribute("cartError", cartError);
            session.removeAttribute("cartError");
        }

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
            // Có thể redirect về trang lỗi
        }

        boolean overStock = false;

        // --- Xử lý tăng/giảm số lượng ---
        if ("increase".equals(action) || "decrease".equals(action)) {
            CartItem item = cart.getItems().get(productId);
            if (item != null) {
                if ("increase".equals(action)) {
                    if (item.getQuantity() < item.getProduct().getStock()) {
                        item.setQuantity(item.getQuantity() + 1);
                    } else {
                        overStock = true;
                    }
                }
                if ("decrease".equals(action) && item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                }
            }
        } else {
            switch (action) {
                case "add":
                    if (product != null) {
                        int stock = product.getStock();
                        CartItem item = cart.getItems().get(productId);
                        int current = (item != null) ? item.getQuantity() : 0;
                        int newQty = current + quantity;
                        if (newQty > stock) {
                            session.setAttribute("cartError", "Số lượng đã đạt tối đa tồn kho cho sản phẩm này!");
                            // Chỉ tăng lên tối đa số lượng tồn kho còn lại
                            int addable = stock - current;
                            if (addable > 0) {
                                cartService.addToCart(cart, product, addable);
                            }
                        } else {
                            cartService.addToCart(cart, product, quantity);
                        }
                    }
                    break;
                case "update":
                    cartService.updateCartItem(cart, productId, quantity);
                    break;
                case "remove":
                    cartService.removeCartItem(cart, productId);
                    break;
            }
        }

        session.setAttribute("cart", cart);

        // Nếu có lỗi vượt stock khi increase, truyền thông báo
        if (overStock) {
            session.setAttribute("cartError", "Số lượng đã đạt tối đa tồn kho cho sản phẩm này!");
        }

        response.sendRedirect(request.getContextPath() + "/carts");
    }
}
