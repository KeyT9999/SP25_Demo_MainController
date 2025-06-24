package orderDao;

import model.Order;
import model.OrderDetail;
import java.sql.SQLException;
import java.util.List;

public interface IOrderDao {

    Order getOrderById(int id);

    void insertOrder(Order order) throws SQLException;

    List<Order> selectAllOrders();

    boolean deleteOrder(int id) throws SQLException;

    boolean updateOrder(Order o) throws SQLException;

    /* Tạo đơn và trả về ID sinh tự động */
    int createOrder(Order order) throws SQLException;

    /* Thêm 1 dòng chi tiết (dùng cho cập-nhật lẻ) */
    void addOrderDetail(int orderId, int productId, int qty, Double price) throws SQLException;

    /* Tạo đơn + nhiều chi tiết trong 1 giao dịch */
    int createOrder(Order order, List<OrderDetail> details) throws SQLException;
}
