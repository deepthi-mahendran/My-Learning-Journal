package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

/**
 * The ShoppingCart class manages a collection of products that a customer
 * intends to purchase. It provides methods for adding, removing, and
 * calculating the total cost of items in the cart.
 * 
 * <p>This class demonstrates encapsulation by maintaining the cart items
 * as a private list and providing controlled access through public methods.</p>
 * 
 * @author Student
 * @version 1.0
 * @since 2026-06-22
 */
public class ShoppingCart {
    
    // Private list to store cart items (encapsulation)
    private List<CartItem> items;
    
    /**
     * Constructs an empty shopping cart.
     */
    public ShoppingCart() {
        this.items = new ArrayList<>();
    }
    
    /**
     * Adds a product to the shopping cart with the specified quantity.
     * 
     * @param product  The product to add
     * @param quantity The quantity to add (must be > 0)
     * @return true if the product was added successfully, false otherwise
     * @throws IllegalArgumentException if product is null or quantity <= 0
     */
    public boolean addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        
        // Check if product already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().getProductID() == product.getProductID()) {
                item.setQuantity(item.getQuantity() + quantity);
                return true;
            }
        }
        
        // Add new item
        items.add(new CartItem(product, quantity));
        return true;
    }
    
    /**
     * Removes a product from the shopping cart.
     * 
     * @param productID The ID of the product to remove
     * @return true if the product was removed, false if not found
     */
    public boolean removeItem(int productID) {
        return items.removeIf(item -> item.getProduct().getProductID() == productID);
    }
    
    /**
     * Updates the quantity of a specific product in the cart.
     * 
     * @param productID The ID of the product to update
     * @param quantity  The new quantity (must be > 0)
     * @return true if updated successfully, false if product not found
     */
    public boolean updateQuantity(int productID, int quantity) {
        if (quantity <= 0) {
            return removeItem(productID);
        }
        
        for (CartItem item : items) {
            if (item.getProduct().getProductID() == productID) {
                item.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Calculates the total cost of all items in the shopping cart.
     * 
     * @return The total cost as a double value
     */
    public double calculateTotal() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
    
    /**
     * Gets the total number of items in the cart (sum of quantities).
     * 
     * @return The total item count
     */
    public int getTotalItemCount() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }
    
    /**
     * Gets the list of cart items.
     * 
     * @return An unmodifiable list of cart items
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(items); // Return a copy for encapsulation
    }
    
    /**
     * Clears all items from the shopping cart.
     */
    public void clear() {
        items.clear();
    }
    
    /**
     * Checks if the shopping cart is empty.
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Returns a string representation of the shopping cart contents.
     * 
     * @return Formatted cart summary
     */
    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Shopping cart is empty.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Shopping Cart:\n");
        for (CartItem item : items) {
            sb.append("  ").append(item).append("\n");
        }
        sb.append(String.format("Total: $%.2f", calculateTotal()));
        return sb.toString();
    }
    
    /**
     * Inner class representing a single item in the shopping cart.
     * This class encapsulates the relationship between a product and its quantity.
     */
    public static class CartItem {
        private Product product;
        private int quantity;
        
        /**
         * Constructs a cart item with the specified product and quantity.
         * 
         * @param product  The product
         * @param quantity The quantity
         */
        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        
        /**
         * Gets the product.
         * 
         * @return The product
         */
        public Product getProduct() {
            return product;
        }
        
        /**
         * Gets the quantity.
         * 
         * @return The quantity
         */
        public int getQuantity() {
            return quantity;
        }
        
        /**
         * Sets the quantity with validation.
         * 
         * @param quantity The quantity to set (must be > 0)
         * @throws IllegalArgumentException if quantity <= 0
         */
        public void setQuantity(int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero.");
            }
            this.quantity = quantity;
        }
        
        /**
         * Calculates the subtotal for this cart item.
         * 
         * @return The subtotal (price * quantity)
         */
        public double getSubtotal() {
            return product.getPrice() * quantity;
        }
        
        @Override
        public String toString() {
            return String.format("%s x %d = $%.2f", 
                                product.getName(), quantity, getSubtotal());
        }
    }
}
