package service;

import dao.jpa.ProductRepository;
import model.Product;
import java.util.List;

public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product pro) {
        productRepository.save(pro);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.find(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean removeProduct(int id) {
        Product p = productRepository.find(id);
        if (p != null) {
            productRepository.delete(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyProduct(Product pro) {
        productRepository.update(pro);
        return true;
    }
}
