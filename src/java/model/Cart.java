package model;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

public class Cart {
    private Map<Integer, CartItem> items = new HashMap<>();
    public Map<Integer, CartItem> getItems() { return items; }

    public void addToCart(Product product, int quantity) {
    int id = product.getId();
    int stock = product.getStock();

    if (items.containsKey(id)) {
        CartItem item = items.get(id);
        int newQuantity = item.getQuantity() + quantity;
        // Kiểm tra không vượt quá tồn kho
        if (newQuantity > stock) {
            item.setQuantity(stock);
            // Option: có thể log/thông báo "Đã đạt tối đa tồn kho"
        } else {
            item.setQuantity(newQuantity);
        }
    } else {
        // Kiểm tra tồn kho khi add lần đầu
        if (quantity > stock) {
            quantity = stock;
            // Option: có thể log/thông báo "Không đủ hàng, đã set tối đa"
        }
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
