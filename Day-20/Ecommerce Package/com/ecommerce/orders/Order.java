package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.ShoppingCart;
import com.ecommerce.ShoppingCart.CartItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Order class represents a customer order in the e-commerce system.
 * Each order has a unique identifier, associated customer, order items,
 * total amount, status, and timestamp.
 * 
 * <p>This class demonstrates encapsulation by maintaining order details
 * as private fields and providing controlled access through public methods.</p>
 * 
 * @author Student
 * @version 1.0
 * @since 2026-06-22
 */
public class Order {
    
    // Enumeration for order status
    public enum OrderStatus {
        PENDING("Pending"),
        PROCESSING("Processing"),
        SHIPPED("Shipped"),
        DELIVERED("Delivered"),
        CANCELLED("Cancelled");
        
        private final String displayName;
        
        OrderStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Private fields for encapsulation
    private int orderID;
    private Customer customer;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    
    /**
     * Constructs a new Order from a customer's shopping cart.
     * 
     * @param orderID  Unique identifier for the order
     * @param customer The customer placing the order
     * @throws IllegalArgumentException if customer is null or cart is empty
     */
    public Order(int orderID, Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (customer.getCart().isEmpty()) {
            throw new IllegalArgumentException("Cannot create order from empty cart.");
        }
        
        this.orderID = orderID;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.orderDate = LocalDateTime.now();
        
        // Copy items from cart to order
        for (CartItem cartItem : customer.getCart().getItems()) {
            this.items.add(new OrderItem(
                cartItem.getProduct(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice()
            ));
        }
        
        this.totalAmount = calculateTotal();
    }
    
    /**
     * Calculates the total amount for the order.
     * 
     * @return The total amount
     */
    private double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    /**
     * Gets the order ID.
     * 
     * @return The order ID
     */
    public int getOrderID() {
        return orderID;
    }
    
    /**
     * Gets the customer associated with this order.
     * 
     * @return The customer
     */
    public Customer getCustomer() {
        return customer;
    }
    
    /**
     * Gets the list of order items.
     * 
     * @return An unmodifiable list of order items
     */
    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }
    
    /**
     * Gets the total amount of the order.
     * 
     * @return The total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }
    
    /**
     * Gets the current order status.
     * 
     * @return The order status
     */
    public OrderStatus getStatus() {
        return status;
    }
    
    /**
     * Updates the order status.
     * 
     * @param status The new status
     */
    public void setStatus(OrderStatus status) {
        if (status != null) {
            this.status = status;
        }
    }
    
    /**
     * Gets the order date and time.
     * 
     * @return The order date
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    /**
     * Generates a formatted order summary.
     * 
     * @return A string containing the complete order summary
     */
    public String generateOrderSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(60)).append("\n");
        sb.append("                   ORDER SUMMARY\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append(String.format("Order ID: %d\n", orderID));
        sb.append(String.format("Customer: %s (ID: %d)\n", 
                               customer.getName(), customer.getCustomerID()));
        sb.append(String.format("Order Date: %s\n", 
                               orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        sb.append(String.format("Status: %s\n", status.getDisplayName()));
        sb.append("-".repeat(60)).append("\n");
        sb.append("Items:\n");
        
        for (OrderItem item : items) {
            sb.append(String.format("  %s x %d @ $%.2f = $%.2f\n",
                                   item.getProductName(),
                                   item.getQuantity(),
                                   item.getUnitPrice(),
                                   item.getSubtotal()));
        }
        
        sb.append("-".repeat(60)).append("\n");
        sb.append(String.format("TOTAL: $%.2f\n", totalAmount));
        sb.append("=".repeat(60));
        
        return sb.toString();
    }
    
    /**
     * Returns a string representation of the order.
     * 
     * @return Formatted order information
     */
    @Override
    public String toString() {
        return String.format("Order #%d | Customer: %s | Total: $%.2f | Status: %s",
                            orderID, customer.getName(), totalAmount, status.getDisplayName());
    }
    
    /**
     * Inner class representing a single item in an order.
     * This class encapsulates the product details at the time of order.
     */
    public static class OrderItem {
        private String productName;
        private int quantity;
        private double unitPrice;
        
        /**
         * Constructs an order item.
         * 
         * @param product   The product
         * @param quantity  The quantity
         * @param unitPrice The unit price at the time of order
         */
        public OrderItem(com.ecommerce.Product product, int quantity, double unitPrice) {
            this.productName = product.getName();
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
        
        /**
         * Gets the product name.
         * 
         * @return The product name
         */
        public String getProductName() {
            return productName;
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
         * Gets the unit price.
         * 
         * @return The unit price
         */
        public double getUnitPrice() {
            return unitPrice;
        }
        
        /**
         * Calculates the subtotal for this order item.
         * 
         * @return The subtotal (unitPrice * quantity)
         */
        public double getSubtotal() {
            return unitPrice * quantity;
        }
    }
}