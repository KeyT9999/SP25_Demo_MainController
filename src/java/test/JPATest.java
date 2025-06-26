package test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class JPATest {
    public static void main(String[] args) {
        try {
            System.out.println("Testing JPA configuration...");
            System.out.println("Available persistence units:");
            
            // Try to create EntityManagerFactory
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShopPU");
            System.out.println("SUCCESS: EntityManagerFactory created successfully!");
            emf.close();
            System.out.println("EntityManagerFactory closed successfully!");
            
        } catch (PersistenceException e) {
            System.err.println("ERROR: Failed to create EntityManagerFactory");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR: Unexpected error");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 