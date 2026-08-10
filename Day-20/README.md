# E-Commerce System (GUI)

A complete Java Swing desktop application for managing an e-commerce store. This system demonstrates the use of packages, imports, and object-oriented principles to provide a user-friendly interface for browsing products, managing a shopping cart, and placing orders.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Compilation and Execution](#compilation-and-execution)
- [Usage Guide](#usage-guide)
- [Key Components](#key-components)
- [Customization](#customization)
- [Extending the System](#extending-the-system)
- [License](#license)

---

## Overview

The **E-Commerce System (GUI)** is a Java Swing application that provides a complete shopping experience:

- Browse a catalog of products with real-time stock information.
- Add products to a shopping cart with quantity selection.
- View and manage cart items.
- Place orders with automatic stock reduction.
- Track order status with a visual summary.

The application demonstrates key Java concepts: **packages**, **import statements**, **encapsulation**, **inheritance**, **polymorphism**, and **GUI development with Swing**.

---

## Features

| Feature                     | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| **Product Catalog**         | Browse products with ID, name, price, and stock quantity.                                       |
| **Add to Cart**             | Select a product and specify quantity (with stock validation).                                   |
| **Shopping Cart**           | View cart items with quantities and subtotals.                                                   |
| **Remove from Cart**        | Remove individual items or clear the entire cart.                                                |
| **Stock Management**        | Stock is automatically reduced when adding to cart and restored when removing items.            |
| **Order Placement**         | Convert cart contents into an order.                                                            |
| **Order Summary**           | View a detailed order summary with items, totals, and status.                                   |
| **Order Status Tracking**   | Update order status via dropdown (Pending → Processing → Shipped → Delivered → Cancelled).      |
| **Customer Management**     | Simple customer profile with a persistent shopping cart.                                        |
| **Validation**              | Input validation on product prices, stock quantities, and customer details.                     |
| **GUI Interface**           | Clean, split-pane layout with tables for products and cart.                                     |
| **Package Organization**    | Demonstrates proper Java package structure and import usage.                                    |

---

## Technology Stack

- **Java SE** — Core language and standard library.
- **Java Swing** — GUI framework for the desktop interface.
- **Java Collections Framework** — `ArrayList`, `List`, and custom collections.
- **Java Time API** — `LocalDateTime` for order timestamps.

**No external dependencies** — pure Java standard library.

---

## Project Structure

The project is organized into two packages, demonstrating proper Java package structure:

```
ECommerceGUI.java          # Main application class (GUI)
com.ecommerce/
├── Product.java           # Product model with validation
├── Customer.java          # Customer model with cart management
├── ShoppingCart.java      # Shopping cart with CartItem inner class
└── com.ecommerce.orders/
    └── Order.java         # Order model with OrderItem inner class and OrderStatus enum
```

### Package Breakdown

| Package                 | Classes                                     | Purpose                                                       |
|-------------------------|---------------------------------------------|---------------------------------------------------------------|
| `com.ecommerce`         | `Product`, `Customer`, `ShoppingCart`       | Core e-commerce domain model and business logic.              |
| `com.ecommerce.orders`  | `Order`                                     | Order-related models, status enum, and order item details.   |

---

## Requirements

- **Java** — JDK 8 or higher (uses `String.repeat()` which is available from Java 11; Java 8 users can replace with loops).
- **No external dependencies** — pure Java SE.

> **Note:** The code uses `String.repeat()` which was introduced in Java 11. If using Java 8, replace `"=".repeat(60)` with a helper method or loop.

---

## Compilation and Execution

### 1. Compile all source files

Make sure all `.java` files are in their correct package directories:

```
ECommerceGUI.java
com/ecommerce/Product.java
com/ecommerce/Customer.java
com/ecommerce/ShoppingCart.java
com/ecommerce/orders/Order.java
```

Then compile from the root directory:

```bash
javac ECommerceGUI.java com/ecommerce/*.java com/ecommerce/orders/*.java
```

### 2. Run the application

```bash
java ECommerceGUI
```

### 3. Alternative: Compile and run in one step

```bash
javac *.java com/ecommerce/*.java com/ecommerce/orders/*.java && java ECommerceGUI
```

---

## Usage Guide

### Step 1: Launch the Application

The main window appears with three panels:

- **Left Panel (Top)**: Product catalog with a table of available products.
- **Left Panel (Bottom)**: Shopping cart with item list and total.
- **Right Panel**: Order management with order summary and status controls.

### Step 2: Browse Products

- The **Product Table** shows all available products with:
  - Product ID
  - Name
  - Price
  - Stock Quantity

### Step 3: Add Products to Cart

1. Select a product row in the product table.
2. Use the **Quantity Spinner** (below the product table) to set the quantity.
3. Click **Add to Cart**.
4. The product is added to your cart, and stock is reduced.

> **Note:** Stock validation prevents adding more than the available quantity.

### Step 4: Manage Your Cart

- **View Cart**: The cart table shows product name, quantity, price, and subtotal.
- **Remove Selected**: Select a row in the cart and click **Remove Selected** to remove the item.
- **Clear Cart**: Remove all items from the cart (stock is restored).
- **Cart Info**: The bottom of the cart panel shows total items and total price.

### Step 5: Place an Order

1. Add products to your cart.
2. Click **Place Order**.
3. The order is created, and the cart is cleared.
4. An order summary appears in the right panel with:
   - Order ID
   - Customer information
   - Order date and time
   - Items with quantities and prices
   - Total amount
   - Current status

### Step 6: Update Order Status

- Use the **Update Status** dropdown to change the order status:
  - Pending
  - Processing
  - Shipped
  - Delivered
  - Cancelled

---

## Key Components

### Product

```java
public class Product {
    private int productID;
    private String name;
    private double price;
    private int stockQuantity;
    
    public Product(int productID, String name, double price, int stockQuantity) {
        setPrice(price);          // Validation applied
        setStockQuantity(stockQuantity);
    }
    
    public boolean reduceStock(int quantity) { ... }
}
```

**Features:**
- Validation on price and stock quantity.
- `reduceStock()` method for safe stock reduction.

### Customer

```java
public class Customer {
    private int customerID;
    private String name;
    private ShoppingCart cart;
    
    public Order placeOrder(int orderID) { ... }
}
```

**Features:**
- Each customer has a `ShoppingCart`.
- `placeOrder()` creates an `Order` from the cart contents.

### ShoppingCart

```java
public class ShoppingCart {
    private List<CartItem> items;
    
    public boolean addItem(Product product, int quantity) { ... }
    public double calculateTotal() { ... }
    public static class CartItem { ... }
}
```

**Features:**
- Adds/removes products with quantity management.
- Calculates total cost.
- Inner `CartItem` class encapsulates product-quantity pairs.

### Order

```java
public class Order {
    public enum OrderStatus { PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED }
    private List<OrderItem> items;
    private LocalDateTime orderDate;
    
    public String generateOrderSummary() { ... }
    public static class OrderItem { ... }
}
```

**Features:**
- Enum for order status with display names.
- Order items snapshot product details at order time.
- `generateOrderSummary()` creates a formatted order summary.

---

## GUI Components

The GUI is built using **Swing** with the following key components:

| Component             | Purpose                                                                 |
|-----------------------|-------------------------------------------------------------------------|
| `JTable`              | Displays product catalog and shopping cart items.                      |
| `DefaultTableModel`   | Provides editable/non-editable table data models.                      |
| `JSplitPane`          | Divides the UI into left (products/cart) and right (order management). |
| `JSpinner`            | Quantity selector for adding products to the cart.                     |
| `JComboBox`           | Order status selection dropdown.                                       |
| `JTextArea`           | Displays the order summary.                                            |
| `JLabel`              | Cart info labels (items count and total).                              |
| `JButton`             | Action buttons for add, remove, clear, and place order.                |

---

## Customization

### Adding New Products

Edit the `initializeProducts()` method in `ECommerceGUI`:

```java
private void initializeProducts() {
    products = new ArrayList<>();
    products.add(new Product(101, "Laptop", 899.99, 10));
    // Add more products here
}
```

### Changing Customer Details

Update the `currentCustomer` initialization in the constructor:

```java
currentCustomer = new Customer(1, "Your Customer Name");
```

### Modifying Order Statuses

Edit the `OrderStatus` enum in `Order.java`:

```java
public enum OrderStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");
    // Add new statuses as needed
}
```

### Changing Look and Feel

In the `main()` method, the system look and feel is used:

```java
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
```

To use a different theme, replace with:

```java
UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
```

### Adding Product Images

To add product images, modify the product table model to include an image column, or add a custom `TableCellRenderer`:

```java
// Example: Add a column for images
String[] columns = {"ID", "Name", "Price", "Stock", "Image"};
```

---

## Extending the System

### Add a Payment System

- Create a `Payment` class with methods for credit card, PayPal, etc.
- Integrate into the `Order` class.

### Add User Authentication

- Create a `User` class with username/password.
- Add a login screen before launching the main GUI.

### Add a Database Backend

- Replace in-memory `ArrayList` with JDBC calls to a database.
- Use a DAO (Data Access Object) pattern.

### Add Product Categories

- Add a `Category` field to `Product`.
- Filter products by category in the GUI.

### Add Discounts / Promotions

- Add a `discount` field to `Product`.
- Apply discounts during cart calculation.

### Add Order History

- Store orders in a `List<Order>` for each customer.
- Display order history in a separate tab.

### Add Shipping Address

- Add address fields to `Customer` or `Order`.
- Include address in the order summary.

### Add Error Logging

- Use `java.util.logging.Logger` to log errors and exceptions.

---

## Best Practices Demonstrated

- **Encapsulation**: All fields are private with public getters/setters.
- **Validation**: Input validation in setters and constructors.
- **Package Structure**: Proper separation of concerns across packages.
- **Import Statements**: Clean imports from both `com.ecommerce` and `com.ecommerce.orders`.
- **Enum Usage**: `OrderStatus` enum with display names.
- **Inner Classes**: `CartItem` and `OrderItem` encapsulate related data.
- **Immutability**: `Order` items snapshot product details.
- **GUI Best Practices**: `SwingUtilities.invokeLater()` for thread safety.

---

## License

This project is created for **educational purposes** — to demonstrate Java programming techniques including GUI development, package management, encapsulation, and object-oriented design. You are free to use, modify, and distribute this code for learning.

---

**Author:** Student Developer  
**Version:** 1.0  
**Date:** June 2026
