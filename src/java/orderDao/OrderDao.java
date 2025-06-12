package orderDao;

import dao.DBConnection;
import model.Order;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao implements IOrderDao {
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM Orders WHERE id = ?";
    private static final String INSERT_ORDER = "INSERT INTO Orders (user_id, total_price, status) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_DETAILS = "INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

    // Lấy order theo id
    @Override
    public Order getOrderById(int id) {
        Order order = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ORDER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = Order.fromResultSet(rs);  // Sử dụng method đã tạo ở class Order
            } else {
                System.out.println("Order not found!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

    // Thêm mới order (có thể trả về id mới tạo)
    @Override
    public void insertOrder(Order orderObj) throws SQLException {
        // Viết tương tự như createOrder, không trả về id
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ORDER)) {
            ps.setInt(1, orderObj.getUserId());
            ps.setDouble(2, orderObj.getTotalPrice());
            ps.setString(3, orderObj.getStatus());
            ps.executeUpdate();
        }
    }

    // Lấy tất cả order
    @Override
    public List<Order> selectAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(Order.fromResultSet(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    // Xóa order theo id
    @Override
    public boolean deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM Orders WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Cập nhật order
    @Override
    public boolean updateOrder(Order orderObj) throws SQLException {
        String sql = "UPDATE Orders SET user_id=?, total_price=?, status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderObj.getUserId());
            ps.setDouble(2, orderObj.getTotalPrice());
            ps.setString(3, orderObj.getStatus());
            ps.setInt(4, orderObj.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Tạo mới order, trả về id (sử dụng khi muốn thêm đồng bộ OrderDetail)
    @Override
    public int createOrder(Order order) {
        int orderId = -1;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderId;
    }

    // Thêm chi tiết order (OrderDetail)
    @Override
    public void addOrderDetail(int orderId, int productId, int quantity, Double price) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER_DETAILS)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
