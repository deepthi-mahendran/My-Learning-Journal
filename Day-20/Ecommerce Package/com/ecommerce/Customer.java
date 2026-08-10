package com.ecommerce;

import com.ecommerce.orders.Order;

/**
 * The Customer class represents a customer in the e-commerce system.
 * Each customer has a unique identifier, name, and a shopping cart.
 * 
 * <p>This class encapsulates customer data and provides methods for
 * cart management and order placement.</p>
 * 
 * @author Student
 * @version 1.0
 * @since 2026-06-22
 */
public class Customer {
    
    // Private fields for encapsulation
    private int customerID;
    private String name;
    private ShoppingCart cart;
    
    /**
     * Constructs a new Customer with the specified attributes.
     * 
     * @param customerID Unique identifier for the customer
     * @param name       Name of the customer
     */
    public Customer(int customerID, String name) {
        this.customerID = customerID;
        setName(name);
        this.cart = new ShoppingCart();
    }
    
    /**
     * Gets the customer ID.
     * 
     * @return The customer ID
     */
    public int getCustomerID() {
        return customerID;
    }
    
    /**
     * Sets the customer ID.
     * 
     * @param customerID The customer ID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    /**
     * Gets the customer name.
     * 
     * @return The customer name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the customer name with validation.
     * 
     * @param name The customer name to set
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.name = name;
    }
    
    /**
     * Gets the customer's shopping cart.
     * 
     * @return The shopping cart
     */
    public ShoppingCart getCart() {
        return cart;
    }
    
    /**
     * Adds a product to the customer's shopping cart.
     * 
     * @param product  The product to add
     * @param quantity The quantity to add
     * @return true if added successfully
     */
    public boolean addToCart(Product product, int quantity) {
        return cart.addItem(product, quantity);
    }
    
    /**
     * Removes a product from the customer's shopping cart.
     * 
     * @param productID The ID of the product to remove
     * @return true if removed successfully
     */
    public boolean removeFromCart(int productID) {
        return cart.removeItem(productID);
    }
    
    /**
     * Places an order using the current contents of the shopping cart.
     * 
     * @param orderID The unique identifier for the order
     * @return An Order object representing the placed order
     * @throws IllegalStateException if the cart is empty
     */
    public Order placeOrder(int orderID) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cannot place order: Shopping cart is empty.");
        }
        
        // Create the order
        Order order = new Order(orderID, this);
        
        // Clear the cart after order placement
        cart.clear();
        
        return order;
    }
    
    /**
     * Returns a string representation of the customer.
     * 
     * @return Formatted customer information
     */
    @Override
    public String toString() {
        return String.format("Customer ID: %d | Name: %s | Cart Items: %d", 
                            customerID, name, cart.getTotalItemCount());
    }
}