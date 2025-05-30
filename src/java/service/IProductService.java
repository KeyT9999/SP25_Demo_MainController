package service;

import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductService {

    /* CREATE */
    void addProduct(Product pro) throws SQLException;

    /* READ (single) */
    Product getProductById(int id) throws SQLException;

    /* READ (all) */
    List<Product> getAllProducts() throws SQLException;

    /* DELETE */
    boolean removeProduct(int id) throws SQLException;

    /* UPDATE */
    boolean modifyProduct(Product pro) throws SQLException;
}
