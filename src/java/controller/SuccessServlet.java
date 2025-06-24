
/*
 * SuccessServlet – gửi mail xác nhận khi khách nhấn “Tôi đã chuyển khoản”
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.EmailUtil;

@WebServlet(name = "SuccessServlet", urlPatterns = {"/success"})
public class SuccessServlet extends HttpServlet {

    /** xử lý chung cho GET/POST */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=== [SUCCESS] ĐÃ VÀO SuccessServlet ===");

        /* --- lấy thông tin lưu ở ConfirmOrderServlet --- */
        HttpSession session = request.getSession();
        String email     = (String) session.getAttribute("lastEmail");
        String fullname  = (String) session.getAttribute("lastFullname");
        String orderId   = (String) session.getAttribute("lastOrderId");
        String totalStr  = (String) session.getAttribute("lastTotalStr");

        System.out.println("lastEmail    = " + email);
        System.out.println("lastFullname = " + fullname);
        System.out.println("lastOrderId  = " + orderId);
        System.out.println("lastTotalStr = " + totalStr);

        /* --- gửi mail xác nhận chuyển khoản --- */
        if (email != null && !email.isEmpty()) {
            String subject = "Cảm ơn bạn đã chuyển khoản!";
            String content = "<h3>Shop KeyT xác nhận đã nhận yêu cầu chuyển khoản.</h3>"
                           + "<p>Đơn hàng của bạn sẽ được xử lý sớm nhất.</p>"
                           + "<p><b>Mã đơn:</b> " + orderId   + "</p>"
                           + "<p><b>Khách hàng:</b> " + fullname + "</p>"
                           + "<p><b>Tổng tiền:</b> " + totalStr + " VNĐ</p>";

            System.out.println("=== [SUCCESS] TRƯỚC khi gửi mail CK ===");
            try {
                EmailUtil.sendMail(email, subject, content);
                System.out.println("=== [SUCCESS] SAU  khi gửi mail CK ===");
            } catch (Exception e) {
                System.out.println("[SUCCESS] LỖI khi gửi mail CK:");
                e.printStackTrace();
            }
        } else {
            System.out.println("[SUCCESS] Không tìm thấy email trong session → không gửi mail.");
        }

        /* --- forward đến trang cảm ơn --- */
        request.getRequestDispatcher("cart/thanks.jsp").forward(request, response);
    }

    @Override protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }

    @Override public String getServletInfo() { return "Servlet gửi mail xác nhận CK"; }
}
