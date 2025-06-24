/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;



import model.Order;
import java.util.List;
import model.CartItem;

public interface IOrderService {
    // Thêm mới một đơn hàng
    void insertOrder(Order order);

    // Lấy thông tin đơn hàng theo id
    Order getOrderById(int id);

    // Lấy tất cả các đơn hàng
    List<Order> selectAllOrders();

    // Xóa đơn hàng theo id
    boolean deleteOrder(int id);

    // Cập nhật thông tin đơn hàng
    boolean updateOrder(Order order);

    // Tạo đơn hàng mới, trả về id của order vừa tạo
    int createOrder(Order order);

    // Thêm chi tiết đơn hàng (OrderDetail)
    void addOrderDetail(int orderId, int productId, int quantity, Double price);
int placeOrder(Order order, List<CartItem> items) throws Exception;
    // Có thể thêm các method nghiệp vụ cao hơn nếu muốn
}
