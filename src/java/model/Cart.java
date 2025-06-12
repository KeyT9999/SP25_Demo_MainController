package model;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

public class Cart {
    private Map<Integer, CartItem> items = new HashMap<>();
    public Map<Integer, CartItem> getItems() { return items; }

    public void addToCart(Product product, int quantity) {
        int id = product.getId();
        if (items.containsKey(id)) {
            CartItem item = items.get(id);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.put(id, new CartItem(product, quantity));
        }
    }

    public void updateCartItem(int productId, int quantity) {
        if (items.containsKey(productId)) {
            items.get(productId).setQuantity(quantity);
        }
    }

    public void removeCartItem(int productId) {
        items.remove(productId);
    }

    // Sửa lỗi phép nhân BigDecimal * int
    public BigDecimal getTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items.values()) {
            // product.getPrice() trả về BigDecimal
            BigDecimal price = item.getProduct().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            total = total.add(price.multiply(quantity));
        }
        return total;
    }
}
