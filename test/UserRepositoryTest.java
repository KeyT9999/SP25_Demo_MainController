package test;

import dao.jpa.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.User;

import java.time.LocalDate;
import java.util.List;

public class UserRepositoryTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShopPU");
        EntityManager em = emf.createEntityManager();
        UserRepository repo = new UserRepository(em);

        // Test save
        User u = new User("testuser", "test@example.com", "VN", "user", true, "123456", LocalDate.now());
        repo.save(u);
        System.out.println("Saved: " + u);

        // Test find
        User found = repo.find(u.getId());
        System.out.println("Found: " + found);

        // Test findAll
        List<User> all = repo.findAll();
        System.out.println("All users: " + all);

        // Test update
        found.setUsername("UpdatedUser");
        repo.update(found);
        System.out.println("Updated: " + repo.find(found.getId()));

        // Test findByEmail
        User byEmail = repo.findByEmail("test@example.com");
        System.out.println("Find by email: " + byEmail);

        // Test login
        User login = repo.findByEmailAndPassword("test@example.com", "123456");
        System.out.println("Login: " + login);

        // Test delete (soft delete)
        repo.delete(found);
        System.out.println("After delete, all: " + repo.findAll());

        em.close();
        emf.close();
    }
} 