package dao.jpa;

import jakarta.persistence.*;
import model.User;
import java.util.List;

public class UserRepository {
    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public void save(User user) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public User find(Integer id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u WHERE u.status = true", User.class)
                 .getResultList();
    }

    public void delete(User user) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Soft-delete: chỉ set status = false
            user.setStatus(false);
            em.merge(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void update(User user) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.status = true", User.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByEmailAndPassword(String email, String password) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.status = true", User.class)
                     .setParameter("email", email)
                     .setParameter("password", password)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
} 