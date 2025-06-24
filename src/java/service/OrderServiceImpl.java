package service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Order;
import model.OrderDetail;
import orderDao.IOrderDao;
import orderDao.OrderDao;
import productDao.ProductDao;

public class OrderServiceImpl implements IOrderService {

    private IOrderDao orderDao;
    private ProductDao productDao;  // BỔ SUNG biến ProductDao

    public OrderServiceImpl() {
        this.orderDao = new OrderDao();
        this.productDao = new ProductDao(); // BỔ SUNG khởi tạo ProductDao
    }

    @Override
    public void insertOrder(Order order) {
        try {
            orderDao.insertOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public List<Order> selectAllOrders() {
        return orderDao.selectAllOrders();
    }

    @Override
    public boolean deleteOrder(int id) {
        try {
            return orderDao.deleteOrder(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateOrder(Order order) {
        try {
            return orderDao.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int createOrder(Order order) {
        try {
            return orderDao.createOrder(order);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void addOrderDetail(int orderId, int productId, int quantity, Double price) {
        try {
            orderDao.addOrderDetail(orderId, productId, quantity, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === NGHIỆP VỤ ĐẶT HÀNG, TRỪ STOCK ===
    @Override
    public int placeOrder(Order order, List<CartItem> cartItems) throws SQLException {
        // ĐẢM BẢO userId hợp lệ
        if (order.getUserId() <= 0) {
            throw new IllegalArgumentException("userId không hợp lệ khi tạo đơn hàng!");
        }
        // 1. Chuyển CartItem → OrderDetail
        List<OrderDetail> details = new ArrayList<>();
        for (CartItem item : cartItems) {
            BigDecimal price = item.getProduct().getPrice();
            int quantity = item.getQuantity();
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(quantity));
            details.add(new OrderDetail(
                    0, // order_id (set sau)
                    item.getProduct().getId(),
                    quantity,
                    lineTotal.doubleValue()
            ));
        }
        // 2. Tạo đơn + chi tiết trong 1 transaction
        int orderId = orderDao.createOrder(order, details);
        if (orderId > 0) {
            // 3. Trừ tồn kho từng sản phẩm
            for (CartItem item : cartItems) {
                productDao.decreaseStock(item.getProduct().getId(), item.getQuantity());
            }
            // Set id cho order (nếu cần dùng lại orderId)
            order.setId(orderId);
        }
        return orderId;
    }
    // Có thể bổ sung thêm các nghiệp vụ logic ở đây nếu muốn!
}
