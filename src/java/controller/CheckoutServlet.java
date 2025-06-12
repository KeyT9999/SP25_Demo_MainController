package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
//import model.CartItem;
//import model.Order;
//import model.Product;
//import service.IOrderService;
//import service.OrderServiceImpl;
//import service.IProductService;
//import service.ProductServiceImpl;

import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart/cart.jsp");
            return;
        }
        // Chỉ forward đến form nhập địa chỉ
        request.getRequestDispatcher("cart/addressForm.jsp").forward(request, response);
    }
}
