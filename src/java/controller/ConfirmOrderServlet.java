package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;
import model.CartItem;
import model.Order;
import model.User;
import service.IOrderService;
import service.OrderServiceImpl;
import util.EmailUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Xử lý bước xác nhận đơn /confirm-order 1) Lấy giỏ hàng + form 2) Gọi
 * OrderService.placeOrder(...) -> trả về orderId 3) Gửi mail + xóa giỏ 4)
 * Forward sang trang QR thanh toán
 */
@WebServlet(name = "ConfirmOrderServlet", urlPatterns = {"/confirm-order"})
public class ConfirmOrderServlet extends HttpServlet {

    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        /* ---------------- Lấy giỏ hàng ---------------- */
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("message", "Giỏ hàng trống!");
            request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
            return;
        }

        /* ---------------- Lấy dữ liệu form ---------------- */
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String note = request.getParameter("note");

        /* ---------------- Xác định user_id ----------------
           - Nếu bạn đã có chức năng đăng nhập, hãy lưu User vào session.
           - Ở đây demo: nếu chưa đăng nhập -> userId = NULL (0) để tránh FK. */
        User user = (User) session.getAttribute("user");
        if (user == null) {
            String message = "Bạn phải đăng nhập trước khi đặt hàng!";
            String encodedMsg = java.net.URLEncoder.encode(message, "UTF-8");
            response.sendRedirect("login.jsp?error=" + encodedMsg);

            return;
        }
        int userId = user.getId();
        if (fullname == null || fullname.isBlank()) {
            fullname = user.getUsername();
        }

        /* ---------------- Tạo Order ---------------- */
        BigDecimal total = cart.getTotalCost();
        Order order = new Order();               // dùng constructor rỗng + setter
        order.setUserId(userId);
        order.setTotalPrice(total.doubleValue());
        order.setStatus("Pending");
        order.setFullname(fullname);
        order.setPhone(phone);
        order.setAddress(address);
        order.setNote(note);

        /* ---------------- Danh sách CartItem ---------------- */
        List<CartItem> items = new ArrayList<>(cart.getItems().values());

        /* ---------------- Gọi service ---------------- */
        int orderId = -1;
        try {
            orderId = orderService.placeOrder(order, items);   // trả về id sinh ra
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /* ---------------- Xử lý kết quả ---------------- */
        String msg;
        if (orderId > 0) {
            msg = "Đặt hàng thành công!";
            sendConfirmMail(email, fullname, orderId, total);

            /* Lưu thông tin cho bước chuyển khoản */
            session.setAttribute("lastEmail", email);
            session.setAttribute("lastFullname", fullname);
            session.setAttribute("lastOrderId", String.valueOf(orderId));
            session.setAttribute("lastTotalStr", total.toPlainString());

            /* Xóa giỏ */
            session.removeAttribute("cart");
        } else {
            msg = "Đặt hàng thất bại – vui lòng thử lại!";
        }

        /* ---------------- Forward sang QR ---------------- */
        request.setAttribute("message", msg);
        request.setAttribute("fullname", fullname);
        request.setAttribute("total", total);
        request.getRequestDispatcher("cart/qrpayment.jsp").forward(request, response);
    }

    /**
     * Gửi mail xác nhận đơn hàng
     */
    private void sendConfirmMail(String to, String name,
            int orderId, BigDecimal total) {

        if (to == null || to.isBlank()) {
            return;
        }
        String subject = "Xác nhận đơn hàng #" + orderId;
        String content = """
                <h3>Xin chào %s,</h3>
                <p>Cảm ơn bạn đã đặt hàng tại <b>Shop KeyT</b>.</p>
                <p><b>Mã đơn:</b> %d</p>
                <p><b>Tổng tiền:</b> %s VNĐ</p>
                <p>Chúng tôi sẽ liên hệ với bạn sớm nhất!</p>
                """.formatted(name, orderId, total.toPlainString());

        try {
            EmailUtil.sendMail(to, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
