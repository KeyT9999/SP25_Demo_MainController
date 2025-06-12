package orderDao;

import java.sql.SQLException;
import java.util.List;
import model.Order;

public interface IOrderDao {
    // Thêm mới đơn hàng (Order)
    public void insertOrder(Order orderObj) throws SQLException;

    // Lấy thông tin đơn hàng theo id
    public Order getOrderById(int id);

    // Lấy tất cả đơn hàng
    public List<Order> selectAllOrders();

    // Xóa đơn hàng theo id
    public boolean deleteOrder(int id) throws SQLException;

    // Cập nhật đơn hàng
    public boolean updateOrder(Order orderObj) throws SQLException;

    // Tạo mới đơn hàng (thường trả về id của đơn hàng vừa tạo)
    public int createOrder(Order order);

    // Thêm chi tiết đơn hàng (OrderDetail)
    public void addOrderDetail(int orderId, int productId, int quantity, Double price);
}
