package productDao;

import dao.DBConnection;
import model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProductDao {

    /* ================== SQL ================== */
    private static final String INSERT_PRODUCT = """
        INSERT INTO Product (name, price, description, stock, importDate, status)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    private static final String SELECT_PRODUCT_BY_ID =
        "SELECT * FROM Product WHERE id = ?";

    /* chỉ lấy sản phẩm còn hiệu lực */
    private static final String SELECT_ALL_PRODUCTS =
        "SELECT * FROM Product WHERE status = 1";

    /* thùng rác (sản phẩm ẩn) */
    private static final String SELECT_ALL_INACTIVE =
        "SELECT * FROM Product WHERE status = 0";

    private static final String UPDATE_PRODUCT = """
        UPDATE Product
        SET name = ?, price = ?, description = ?, stock = ?, importDate = ?, status = ?
        WHERE id = ?
    """;

    /* xoá mềm – đặt status = 0 */
    private static final String SOFT_DELETE =
        "UPDATE Product SET status = 0 WHERE id = ?";

    /* ============= INSERT ============= */
    @Override
    public void insertProduct(Product p) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_PRODUCT)) {

            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setString(3, p.getDescription());
            ps.setInt(4, p.getStock());
            ps.setTimestamp(5, toSqlTimestamp(p.getImportDate()));
            ps.setBoolean(6, p.isStatus());

            ps.executeUpdate();
        }
    }

    /* ============= SELECT (single) ============= */
    @Override
    public Product selectProduct(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_PRODUCT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? extractProduct(rs) : null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /* ============= SELECT (all active) ============= */
    @Override
    public List<Product> selectAllProducts() {
        List<Product> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(extractProduct(rs));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /* ============= SELECT (inactive / trash) ============= */
    public List<Product> selectInactiveProducts() {
        List<Product> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_INACTIVE);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(extractProduct(rs));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /* ============= UPDATE ============= */
    @Override
    public boolean updateProduct(Product p) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE_PRODUCT)) {

            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setString(3, p.getDescription());
            ps.setInt(4, p.getStock());
            ps.setTimestamp(5, toSqlTimestamp(p.getImportDate()));
            ps.setBoolean(6, p.isStatus());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /* ============= SOFT DELETE ============= */
    @Override
    public boolean deleteProduct(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SOFT_DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /* ---------- Helper ---------- */
    private Timestamp toSqlTimestamp(LocalDateTime ldt) {
        return ldt == null ? null : Timestamp.valueOf(ldt);
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBigDecimal("price"),
            rs.getString("description"),
            rs.getInt("stock"),
            rs.getTimestamp("importDate") == null
                ? null
                : rs.getTimestamp("importDate").toLocalDateTime(),
            rs.getBoolean("status")
        );
    }
    
    public boolean decreaseStock(int productId, int amount) throws SQLException {
    String sql = "UPDATE Product SET stock = stock - ? WHERE id = ? AND stock >= ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, amount);
        ps.setInt(2, productId);
        ps.setInt(3, amount);
        return ps.executeUpdate() > 0;
    }
}


    /* ---------- MAIN quick test ---------- */
    public static void main(String[] args) {
        ProductDao dao = new ProductDao();
        List<Product> list = dao.selectAllProducts();
        System.out.println("Active products: " + list.size());
        list.forEach(System.out::println);
    }
}
