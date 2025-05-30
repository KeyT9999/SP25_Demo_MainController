package model;

import java.time.LocalDate;

/**
 * Entity đại diện bản ghi trong bảng Users.
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String country;
    private String role;
    private boolean status;   // true = active
    private String password;
    private LocalDate dob;

    /**
     * Constructor mặc định (bắt buộc cho JavaBean)
     */
    public User() {
    }

    /**
     * Constructor lấy đủ trường (dùng cho SELECT, UPDATE)
     */
    public User(int id, String username, String email,
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
        this(0, username, email, country, role, status, password, dob);
    }

    /* ---------- Getter / Setter ---------- */
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
