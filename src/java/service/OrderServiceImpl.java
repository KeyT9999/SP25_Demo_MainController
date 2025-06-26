package service;

import dao.jpa.OrderRepository;
import dao.jpa.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Order;
import model.OrderDetail;
import model.Product;

public class OrderServiceImpl implements IOrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void insertOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.find(id);
    }

    @Override
    public List<Order> selectAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public boolean deleteOrder(int id) {
        Order order = orderRepository.find(id);
        if (order != null) {
            orderRepository.delete(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        try {
            orderRepository.update(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int createOrder(Order order) {
        try {
            orderRepository.save(order);
            return order.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void addOrderDetail(int orderId, int productId, int quantity, Double price) {
        Order order = orderRepository.find(orderId);
        Product product = productRepository.find(productId);
        if (order != null && product != null) {
            OrderDetail detail = new OrderDetail(order, product, quantity, price);
            if (order.getOrderDetails() == null) {
                order.setOrderDetails(new ArrayList<>());
            }
            order.getOrderDetails().add(detail);
            orderRepository.update(order);
        }
    }

    @Override
    public int placeOrder(Order order, List<CartItem> cartItems) {
        if (order.getUser() == null || order.getUser().getId() == null || order.getUser().getId() <= 0) {
            throw new IllegalArgumentException("User không hợp lệ khi tạo đơn hàng!");
        }

        List<OrderDetail> details = new ArrayList<>();
        for (CartItem item : cartItems) {
            BigDecimal price = item.getProduct().getPrice();
            int quantity = item.getQuantity();
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(quantity));
            OrderDetail detail = new OrderDetail(
                order,
                item.getProduct(),
                quantity,
                lineTotal.doubleValue()
            );
            details.add(detail);
        }

        order.setOrderDetails(details);
        orderRepository.save(order);

        // Trừ tồn kho từng sản phẩm (nếu cần)
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int newStock = product.getStock() - item.getQuantity();
            if (newStock >= 0) {
                product.setStock(newStock);
                productRepository.update(product);
            }
        }
        return order.getId();
    }

    public List<Order> findByUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
