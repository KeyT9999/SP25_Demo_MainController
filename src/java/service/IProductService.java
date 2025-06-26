package service;

import java.util.List;
import model.Product;

public interface IProductService {

    /* CREATE */
    void addProduct(Product pro);

    /* READ (single) */
    Product getProductById(int id);

    /* READ (all) */
    List<Product> getAllProducts();

    /* DELETE */
    boolean removeProduct(int id);

    /* UPDATE */
    boolean modifyProduct(Product pro);
}
