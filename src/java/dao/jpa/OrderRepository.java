package dao.jpa;

import jakarta.persistence.*;
import model.Order;
import java.util.List;

public class OrderRepository {
    private EntityManager em;

    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Order order) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(order);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Order find(Integer id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class)
                 .getResultList();
    }

    public void update(Order order) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(order);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(Order order) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(order));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Order> findByUserId(Integer userId) {
        return em.createQuery("SELECT o FROM Order o WHERE o.user.id = :uid", Order.class)
                 .setParameter("uid", userId)
                 .getResultList();
    }

    public List<Order> findByStatus(String status) {
        return em.createQuery("SELECT o FROM Order o WHERE o.status = :status", Order.class)
                 .setParameter("status", status)
                 .getResultList();
    }
} 