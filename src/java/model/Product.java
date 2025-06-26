package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @Size(max = 255)
    private String description;

    @NotNull
    @Min(0)
    private int stock;

    private LocalDateTime importDate;   // ngày nhập kho
    private boolean status;       // true = còn bán, false = đã xoá (soft-delete)
    private Integer quantity;
    
    public Product() {
    }

 
    public Product(int id, String name, BigDecimal price, String description,
            int stock, LocalDateTime importDate, boolean status) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.importDate = importDate;
        this.status = status;
    }

   
    public Product(String name, BigDecimal price, String description,
            int stock, LocalDateTime importDate, boolean status) {

        this(0, name, price, description, stock, importDate, status);
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDateTime importDate) {
        this.importDate = importDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
public Integer getQuantity() { return quantity; }


    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + ", description='" + description + '\''
                + ", stock=" + stock
                + ", importDate=" + importDate
                + ", status=" + status
                + '}';
    }
}
