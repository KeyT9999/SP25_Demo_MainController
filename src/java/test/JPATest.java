package test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPATest {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShopPU");
            System.out.println("JPA EntityManagerFactory created successfully!");
            emf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 