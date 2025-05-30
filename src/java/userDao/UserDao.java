package userDao;

import dao.DBConnection;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {

    /* ================== SQL ================== */
    private static final String INSERT_USER =
        "INSERT INTO Users (username, email, country, role, status, password, dob) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_USER_BY_ID =
        "SELECT * FROM Users WHERE id = ?";

    private static final String SELECT_ALL_USERS =
        "SELECT * FROM Users";

    private static final String UPDATE_USER =
        "UPDATE Users SET username = ?, email = ?, country = ?, " +
        "role = ?, status = ?, password = ?, dob = ? WHERE id = ?";

    private static final String DELETE_USER =
        "DELETE FROM Users WHERE id = ?";

    /* ============= INSERT ============= */
    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.setDate  (7, toSqlDate(user.getDob()));
            ps.executeUpdate();
        }
    }

    /* ============= SELECT (single) ============= */
    @Override
    public User selectUser(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? extractUser(rs) : null;
            }
        }
    }

    /* ============= SELECT (all) ============= */
    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(extractUser(rs));
            }
        }
        return users;
    }

    /* ============= UPDATE ============= */
    @Override
    public boolean updateUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_USER)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.setDate  (7, toSqlDate(user.getDob()));
            ps.setInt   (8, user.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /* ============= DELETE ============= */
    @Override
    public boolean deleteUser(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_USER)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /* ============= Helper ============= */
    private User extractUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("country"),
            rs.getString("role"),
            rs.getBoolean("status"),
            rs.getString("password"),
            rs.getDate("dob") == null ? null : rs.getDate("dob").toLocalDate()
        );
    }
    
    public User checkLogin(String email, String password) throws SQLException {
    String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
    try (Connection c = DBConnection.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        return rs.next() ? extractUser(rs) : null;   // dùng helper extractUser bạn đã có
    }
}


    /** Chuyển LocalDate sang java.sql.Date hoặc null-safe */
    private Date toSqlDate(LocalDate ld) {
        return ld == null ? null : Date.valueOf(ld);
    }
}
