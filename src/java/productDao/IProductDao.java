
package productDao;

import java.util.List;
import model.Product;
import java.sql.SQLException;



public interface IProductDao {
    public void insertProduct (Product pro) throws SQLException;
    public Product selectProduct(int id);
    public List<Product>selectAllProducts();
    public boolean deleteProduct(int id) throws SQLException;
    public boolean updateProduct(Product pro) throws SQLException;
    
}
