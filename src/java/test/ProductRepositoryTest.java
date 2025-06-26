/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import model.Product;

public class ProductRepositoryTest {
    public static void main(String[] args) {
        // Test tạo Product object
        Product p = new Product();
        p.setName("Test Pen");
        p.setPrice(new BigDecimal("5.0"));
        p.setQuantity(10);
        p.setStatus(true);
        p.setImportDate(LocalDateTime.now());

        System.out.println("Product created: " + p.getName());
        System.out.println("Price: " + p.getPrice());
        System.out.println("Quantity: " + p.getQuantity());
        System.out.println("Status: " + p.isStatus());
    }
}
