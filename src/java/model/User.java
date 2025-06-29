package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String username;
    
    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    
    private String country;
    private String role;
    private boolean status;   // true = active
    
    @NotNull
    @Size(min = 6, max = 100)
    @Column(nullable = false)
    private String password;
    
    private LocalDate dob;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    /**
     * Constructor mặc định (bắt buộc cho JPA)
     */
    public User() {
    }

    /**
     * Constructor lấy đủ trường (dùng cho SELECT, UPDATE)
     */
    public User(Integer id, String username, String email,
            String country, String role,
            boolean status, String password, LocalDate dob) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.country = country;
        this.role = role;
        this.status = status;
        this.password = password;
        this.dob = dob;
    }

    /**
     * Constructor không id (dùng cho INSERT)
     */
    public User(String username, String email,
            String country, String role,
            boolean status, String password, LocalDate dob) {
        this(null, username, email, country, role, status, password, dob);
    }

    /* ---------- Getter / Setter ---------- */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * JavaBean chuẩn: boolean → isXxx()
     */
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", country='" + country + '\''
                + ", role='" + role + '\''
                + ", status=" + status
                + ", password='" + password + '\''
                + ", dob=" + dob + '\''
                + '}';
    }
}
