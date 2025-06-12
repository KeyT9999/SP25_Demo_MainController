package service;

import model.Cart;
import model.Product;

public class CartServiceImpl implements ICartService {
    @Override
    public void addToCart(Cart cart, Product product, int quantity) {
        cart.addToCart(product, quantity);
    }
    @Override
    public void updateCartItem(Cart cart, int productId, int quantity) {
        cart.updateCartItem(productId, quantity);
    }
    @Override
    public void removeCartItem(Cart cart, int productId) {
        cart.removeCartItem(productId);
    }
}
