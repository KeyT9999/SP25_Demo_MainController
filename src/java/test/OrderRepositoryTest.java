/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.jpa.OrderRepository;
import dao.jpa.UserRepository;
import model.Order;
import model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 *
 * @author Admin
 */
public class OrderRepositoryTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShopPU");
        EntityManager em = emf.createEntityManager();
        OrderRepository orderRepo = new OrderRepository(em);
        UserRepository userRepo = new UserRepository(em);

        // Giả sử đã có user trong DB
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            System.out.println("No user found, please add user first!");
            return;
        }
        User user = users.get(0);

        // Test save
        Order order = new Order(user, 100.0, "Pending");
        orderRepo.save(order);
        System.out.println("Saved: " + order);

        // Test find
        Order found = orderRepo.find(order.getId());
        System.out.println("Found: " + found);

        // Test findAll
        List<Order> all = orderRepo.findAll();
        System.out.println("All orders: " + all);

        // Test update
        found.setStatus("Completed");
        orderRepo.update(found);
        System.out.println("Updated: " + orderRepo.find(found.getId()));

        // Test findByUserId
        List<Order> byUser = orderRepo.findByUserId(user.getId());
        System.out.println("Orders by user: " + byUser);

        // Test findByStatus
        List<Order> byStatus = orderRepo.findByStatus("Completed");
        System.out.println("Orders by status: " + byStatus);

        // Test delete
        orderRepo.delete(found);
        System.out.println("After delete, all: " + orderRepo.findAll());

        em.close();
        emf.close();
    }
}
