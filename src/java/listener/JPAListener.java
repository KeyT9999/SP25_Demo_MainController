package listener;

import jakarta.persistence.*;
import jakarta.servlet.*;

public class JPAListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent e) {
        try {
            System.out.println("Initializing JPA EntityManagerFactory for ShopPU...");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShopPU");
            e.getServletContext().setAttribute("emf", emf);
            System.out.println("JPA EntityManagerFactory initialized successfully");
        } catch (Exception ex) {
            System.err.println("ERROR: Failed to initialize JPA: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("JPA initialization failed", ex);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent e) {
        try {
            EntityManagerFactory factory = (EntityManagerFactory) e.getServletContext().getAttribute("emf");
            if (factory != null && factory.isOpen()) {
                factory.close();
                System.out.println("JPA EntityManagerFactory closed successfully");
            }
        } catch (Exception ex) {
            System.err.println("WARNING: Error closing EntityManagerFactory: " + ex.getMessage());
        }
    }
}
