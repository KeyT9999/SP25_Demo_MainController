package service;

import model.Order;
import orderDao.IOrderDao;
import orderDao.OrderDao;
import java.util.List;

public class OrderServiceImpl implements IOrderService {
    // Khai báo DAO (gọi đến tầng truy xuất DB)
    private IOrderDao orderDao;

    // Constructor: new luôn OrderDao, hoặc inject từ ngoài vào (nếu dùng DI)
    public OrderServiceImpl() {
        this.orderDao = new OrderDao();
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
        return orderDao.createOrder(order);
    }

    @Override
    public void addOrderDetail(int orderId, int productId, int quantity, Double price) {
        orderDao.addOrderDetail(orderId, productId, quantity, price);
    }

    // Chị có thể bổ sung thêm các nghiệp vụ logic ở đây nếu muốn!
}
