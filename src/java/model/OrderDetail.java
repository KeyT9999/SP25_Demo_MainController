package model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OrderDetail")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")   // khóa ngoại đến bảng Order
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id") // khóa ngoại đến bảng Product
    private Product product;

    private Integer quantity;

    private Double price;   // đơn giá * quantity hoặc giá bán

    public OrderDetail() {
    }

    public OrderDetail(Order order, Product product, Integer quantity, Double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
