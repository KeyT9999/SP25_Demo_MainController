package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import model.CartItem;
import model.Order;
import model.Cart;
import model.Product;
import service.IOrderService;
import service.IProductService;
import service.OrderServiceImpl;
import service.ProductServiceImpl;

@WebServlet(name = "ConfirmOrderServlet", urlPatterns = {"/confirm-order"})
public class ConfirmOrderServlet extends HttpServlet {
    private IOrderService orderService = new OrderServiceImpl();
    private IProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart/cart.jsp");
            return;
        }
        // Lấy dữ liệu từ form
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String note = request.getParameter("note");

        // (Tùy DB, lưu thêm fullname/phone/address vào bảng Orders hoặc 1 bảng riêng)
        int userId = 1; // Nếu có user login thì lấy từ session
        java.math.BigDecimal totalPrice = cart.getTotalCost();
        Order order = new Order(0, userId, totalPrice.doubleValue(), "Pending");
        order.setFullname(fullname);
        order.setPhone(phone);
        order.setAddress(address);
        order.setNote(note);

        int orderId = -1;
        try {
            orderId = orderService.createOrder(order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (orderId != -1) {
            for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                int pid = entry.getKey();
                CartItem item = entry.getValue();
                Product product = null;
                try {
                    product = productService.getProductById(pid);
                } catch (Exception e) { }
                if (product != null) {
                    orderService.addOrderDetail(orderId, product.getId(), item.getQuantity(), product.getPrice().doubleValue());
                }
            }
        }

        // Clear cart
        session.removeAttribute("cart");
        // Truyền thông tin đơn hàng sang trang QR nếu muốn show tên/tổng tiền
        request.setAttribute("fullname", fullname);
        request.setAttribute("total", totalPrice);
        request.getRequestDispatcher("cart/qrpayment.jsp").forward(request, response);
    }
}
