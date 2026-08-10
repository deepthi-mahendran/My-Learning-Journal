package com.ecommerce;

/**
 * The Product class represents a product available for purchase in the
 * e-commerce system. Each product has a unique identifier, name, price,
 * and stock quantity.
 * 
 * <p>This class follows encapsulation principles by using private fields
 * and public getter/setter methods with input validation.</p>
 * 
 * @author Student
 * @version 1.0
 * @since 2026-06-22
 */
public class Product {
    
    // Private fields for encapsulation
    private int productID;
    private String name;
    private double price;
    private int stockQuantity;
    
    /**
     * Constructs a new Product with the specified attributes.
     * 
     * @param productID     Unique identifier for the product
     * @param name          Name of the product
     * @param price         Price of the product (must be >= 0)
     * @param stockQuantity Available stock quantity (must be >= 0)
     * @throws IllegalArgumentException if price or stockQuantity is negative
     */
    public Product(int productID, String name, double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        setPrice(price);          // Validation applied
        setStockQuantity(stockQuantity); // Validation applied
    }
    
    /**
     * Gets the product ID.
     * 
     * @return The product ID
     */
    public int getProductID() {
        return productID;
    }
    
    /**
     * Sets the product ID.
     * 
     * @param productID The product ID to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    /**
     * Gets the product name.
     * 
     * @return The product name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the product name.
     * 
     * @param name The product name to set
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        this.name = name;
    }
    
    /**
     * Gets the product price.
     * 
     * @return The product price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Sets the product price with validation.
     * 
     * @param price The product price to set (must be >= 0)
     * @throws IllegalArgumentException if price is negative
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }
    
    /**
     * Gets the available stock quantity.
     * 
     * @return The stock quantity
     */
    public int getStockQuantity() {
        return stockQuantity;
    }
    
    /**
     * Sets the stock quantity with validation.
     * 
     * @param stockQuantity The stock quantity to set (must be >= 0)
     * @throws IllegalArgumentException if stockQuantity is negative
     */
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }
    
    /**
     * Reduces the stock quantity by a specified amount.
     * 
     * @param quantity The quantity to reduce (must be > 0 and <= current stock)
     * @return true if stock was successfully reduced, false otherwise
     */
    public boolean reduceStock(int quantity) {
        if (quantity <= 0) {
            return false;
        }
        if (quantity > stockQuantity) {
            return false;
        }
        stockQuantity -= quantity;
        return true;
    }
    
    /**
     * Returns a string representation of the product.
     * 
     * @return Formatted product information
     */
    @Override
    public String toString() {
        return String.format("ID: %d | %s | $%.2f | Stock: %d", 
                            productID, name, price, stockQuantity);
    }
}

