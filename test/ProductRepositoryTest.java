package test;

import dao.jpa.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShopPU");
        EntityManager em = emf.createEntityManager();
        ProductRepository repo = new ProductRepository(em);

        // Test save
        Product p = new Product("Test Product", new BigDecimal("123.45"), "Test Desc", 10, LocalDateTime.now(), true);
        repo.save(p);
        System.out.println("Saved: " + p);

        // Test find
        Product found = repo.find(p.getId());
        System.out.println("Found: " + found);

        // Test findAll
        List<Product> all = repo.findAll();
        System.out.println("All products: " + all);

        // Test update
        found.setName("Updated Name");
        repo.update(found);
        System.out.println("Updated: " + repo.find(found.getId()));

        // Test delete (soft delete)
        repo.delete(found);
        System.out.println("After delete, all: " + repo.findAll());

        em.close();
        emf.close();
    }
} 