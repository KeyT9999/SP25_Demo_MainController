package dao.jpa;

import jakarta.persistence.*;
import model.Product;
import java.util.List;

public class ProductRepository {

    private EntityManager em;

    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Product p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Product find(Integer id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p WHERE p.status = true", Product.class)
                 .getResultList();
    }

    public void delete(Product p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Soft-delete: chỉ set status = false
            p.setStatus(false);
            em.merge(p);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void update(Product p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(p);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
