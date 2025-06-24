package orderDao;

import dao.DBConnection;
import model.Order;
import model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao implements IOrderDao {

    private static final Logger LOG = Logger.getLogger(OrderDao.class.getName());

    /* ---------- SQL ---------- */
    private static final String SQL_ORDER_INSERT =
            "INSERT INTO Orders (user_id,total_price,status,fullname,phone,address,note) "
          + "VALUES (?,?,?,?,?,?,?)";

    private static final String SQL_DETAIL_INSERT =
            "INSERT INTO OrderDetails(order_id,product_id,quantity,price) "
          + "VALUES (?,?,?,?)";

    private static final String SQL_ORDER_FIND   = "SELECT * FROM Orders WHERE id = ?";
    private static final String SQL_ORDER_SELECT = "SELECT * FROM Orders";
    private static final String SQL_ORDER_DELETE = "DELETE FROM Orders WHERE id = ?";
    private static final String SQL_ORDER_UPDATE =
            "UPDATE Orders SET user_id = ?, total_price = ?, status = ?, "
          + "fullname = ?, phone = ?, address = ?, note = ? WHERE id = ?";

    /* ========================================================= */
    /* ===============  TRUY VẤN / CẬP NHẬT CSDL  =============== */
    /* ========================================================= */

    /** Lấy 1 đơn theo id */
    @Override
    public Order getOrderById(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_ORDER_FIND)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Order.fromResultSet(rs) : null;
            }

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "getOrderById", ex);
            return null;
        }
    }

    /** Thêm 1 đơn đơn giản – không cần lấy id sinh ra */
    @Override
    public void insertOrder(Order o) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_ORDER_INSERT)) {

            fillOrder(ps, o);
            ps.executeUpdate();
        }
    }

    /** Lấy tất cả đơn */
    @Override
    public List<Order> selectAllOrders() {
        List<Order> list = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_ORDER_SELECT);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(Order.fromResultSet(rs));

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "selectAllOrders", ex);
        }
        return list;
    }

    /** Xoá đơn theo id */
    @Override
    public boolean deleteOrder(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_ORDER_DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** Cập nhật thông tin đơn */
    @Override
    public boolean updateOrder(Order o) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_ORDER_UPDATE)) {

            fillOrder(ps, o);
            ps.setInt(8, o.getId());          // WHERE id = ?
            return ps.executeUpdate() > 0;
        }
    }

    /** Tạo đơn và trả về id sinh tự động */
    @Override
    public int createOrder(Order o) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_ORDER_INSERT,
                                                        Statement.RETURN_GENERATED_KEYS)) {

            fillOrder(ps, o);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    /** Thêm 1 chi tiết cho đơn đã có sẵn */
    @Override
    public void addOrderDetail(int orderId, int pid, int qty, Double price) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DETAIL_INSERT)) {

            ps.setInt(1, orderId);
            ps.setInt(2, pid);
            ps.setInt(3, qty);
            ps.setDouble(4, price);
            ps.executeUpdate();
        }
    }

    /** Tạo đơn + nhiều chi tiết trong 1 transaction, trả về id */
    @Override
    public int createOrder(Order o, List<OrderDetail> details) throws SQLException {
        Connection c = DBConnection.getConnection();
        c.setAutoCommit(false);

        try (PreparedStatement psO = c.prepareStatement(SQL_ORDER_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psD = c.prepareStatement(SQL_DETAIL_INSERT)) {

            /* 1. Insert Order */
            fillOrder(psO, o);
            psO.executeUpdate();
            ResultSet rs = psO.getGeneratedKeys();
            if (!rs.next()) throw new SQLException("Không lấy được id đơn!");
            int orderId = rs.getInt(1);

            /* 2. Insert hàng loạt OrderDetail */
            for (OrderDetail d : details) {
                psD.setInt(1, orderId);
                psD.setInt(2, d.getProductId());
                psD.setInt(3, d.getQuantity());
                psD.setDouble(4, d.getPrice());
                psD.addBatch();
            }
            psD.executeBatch();

            c.commit();
            return orderId;

        } catch (SQLException ex) {
            c.rollback();
            LOG.log(Level.SEVERE, "createOrder (transaction)", ex);
            throw ex;
        } finally {
            c.setAutoCommit(true);
            c.close();
        }
    }

    /* ========================================================= */
    /* =====================   HÀM HỖ TRỢ   ===================== */
    /* ========================================================= */

    /** Gán các trường vào PreparedStatement theo đúng thứ tự */
    private void fillOrder(PreparedStatement ps, Order o) throws SQLException {
        ps.setObject(1, o.getUserId() == 0 ? null : o.getUserId(), Types.INTEGER);
        ps.setDouble(2, o.getTotalPrice());
        ps.setString(3, o.getStatus());
        ps.setString(4, o.getFullname());
        ps.setString(5, o.getPhone());
        ps.setString(6, o.getAddress());
        ps.setString(7, o.getNote());
    }
}
