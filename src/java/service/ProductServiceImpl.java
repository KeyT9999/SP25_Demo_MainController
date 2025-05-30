package service;

import model.Product;
import productDao.IProductDao;
import productDao.ProductDao;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements IProductService {

    /* Có thể Inject DAO qua constructor (dễ mock UT) */
    private final IProductDao productDao;

    public ProductServiceImpl() {
        this.productDao = new ProductDao();
    }

    /* ----------------- CREATE ----------------- */
    @Override
    public void addProduct(Product pro) throws SQLException {
        productDao.insertProduct(pro);
    }

    /* ----------------- READ ----------------- */
    @Override
    public Product getProductById(int id) throws SQLException {
        return productDao.selectProduct(id);
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        return productDao.selectAllProducts();
    }

    /* ----------------- DELETE ----------------- */
    @Override
    public boolean removeProduct(int id) throws SQLException {
        return productDao.deleteProduct(id);
    }

    /* ----------------- UPDATE ----------------- */
    @Override
    public boolean modifyProduct(Product pro) throws SQLException {
        return productDao.updateProduct(pro);
    }
}
