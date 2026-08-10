import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.ShoppingCart;
import com.ecommerce.orders.Order;
import com.ecommerce.orders.Order.OrderStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ECommerceGUI - A graphical user interface for the e-commerce system.
 * 
 * <p>This class demonstrates the use of packages and import statements by
 * importing classes from com.ecommerce and com.ecommerce.orders packages.
 * It provides a user-friendly interface for browsing products, managing
 * a shopping cart, and placing orders.</p>
 * 
 * @author Student
 * @version 1.0
 * @since 2026-06-22
 */
public class ECommerceGUI extends JFrame {
    
    // Data stores
    private List<Product> products;
    private Customer currentCustomer;
    private int nextOrderID = 1001;
    
    // GUI Components
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JLabel totalLabel;
    private JLabel cartCountLabel;
    private JTextArea orderSummaryArea;
    private JComboBox<String> statusComboBox;
    private JLabel currentOrderLabel;
    private JSpinner qtySpinner;
    
    /**
     * Constructor initializes the GUI and sample data.
     */
    public ECommerceGUI() {
        setTitle("Simple E-Commerce System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Initialize sample data
        initializeProducts();
        currentCustomer = new Customer(1, "John Doe");
        
        // Build the GUI
        buildUI();
        
        // Refresh displays
        refreshProductTable();
        refreshCartTable();
        updateCartInfo();
    }
    
    /**
     * Initializes sample product data.
     */
    private void initializeProducts() {
        products = new ArrayList<>();
        products.add(new Product(101, "Laptop", 899.99, 10));
        products.add(new Product(102, "Smartphone", 599.99, 15));
        products.add(new Product(103, "Headphones", 79.99, 30));
        products.add(new Product(104, "Keyboard", 49.99, 25));
        products.add(new Product(105, "Mouse", 29.99, 40));
        products.add(new Product(106, "Monitor", 299.99, 8));
        products.add(new Product(107, "Printer", 149.99, 12));
        products.add(new Product(108, "Webcam", 89.99, 20));
    }
    
    /**
     * Builds the complete user interface.
     */
    private void buildUI() {
        // Main split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        
        // Left Panel: Products and Cart
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.add(createProductPanel(), BorderLayout.CENTER);
        leftPanel.add(createCartPanel(), BorderLayout.SOUTH);
        
        // Right Panel: Order Management
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.add(createOrderPanel(), BorderLayout.CENTER);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        
        add(splitPane, BorderLayout.CENTER);
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }
    
    /**
     * Creates the top panel with customer info and controls.
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Customer Information"));
        panel.add(new JLabel("Customer: " + currentCustomer.getName() + " (ID: " + 
                            currentCustomer.getCustomerID() + ")"));
        return panel;
    }
    
    /**
     * Creates the bottom panel with action buttons.
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.addActionListener(e -> addSelectedProductToCart());
        
        JButton removeFromCartBtn = new JButton("Remove Selected");
        removeFromCartBtn.addActionListener(e -> removeSelectedFromCart());
        
        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.addActionListener(e -> placeOrder());
        
        JButton clearCartBtn = new JButton("Clear Cart");
        clearCartBtn.addActionListener(e -> clearCart());
        
        panel.add(addToCartBtn);
        panel.add(removeFromCartBtn);
        panel.add(clearCartBtn);
        panel.add(placeOrderBtn);
        
        return panel;
    }
    
    /**
     * Creates the product browsing panel.
     */
    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Available Products"));
        
        // Product table
        String[] columns = {"ID", "Name", "Price", "Stock"};
        productTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(productTableModel);
        productTable.setRowHeight(25);
        productTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(productTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Quantity selector
        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        qtyPanel.add(new JLabel("Quantity:"));
        qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        qtySpinner.setPreferredSize(new Dimension(60, 25));
        qtyPanel.add(qtySpinner);
        panel.add(qtyPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates the shopping cart panel.
     */
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        panel.setPreferredSize(new Dimension(0, 200));
        
        // Cart table
        String[] columns = {"Product", "Qty", "Price", "Subtotal"};
        cartTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        cartTable.setRowHeight(25);
        cartTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(cartTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Cart info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartCountLabel = new JLabel("Items: 0");
        totalLabel = new JLabel("Total: $0.00");
        infoPanel.add(cartCountLabel);
        infoPanel.add(new JLabel("  |  "));
        infoPanel.add(totalLabel);
        panel.add(infoPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates the order management panel.
     */
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Order Management"));
        
        // Order summary area
        orderSummaryArea = new JTextArea();
        orderSummaryArea.setEditable(false);
        orderSummaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(orderSummaryArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Status control panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Update Status:"));
        statusComboBox = new JComboBox<>();
        for (OrderStatus status : OrderStatus.values()) {
            statusComboBox.addItem(status.getDisplayName());
        }
        statusComboBox.addActionListener(e -> updateOrderStatus());
        statusPanel.add(statusComboBox);
        
        currentOrderLabel = new JLabel("No active order");
        statusPanel.add(new JLabel("  |  "));
        statusPanel.add(currentOrderLabel);
        
        panel.add(statusPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Refreshes the product table with current product data.
     */
    private void refreshProductTable() {
        productTableModel.setRowCount(0);
        for (Product p : products) {
            productTableModel.addRow(new Object[]{
                p.getProductID(),
                p.getName(),
                String.format("$%.2f", p.getPrice()),
                p.getStockQuantity()
            });
        }
    }
    
    /**
     * Refreshes the shopping cart table.
     */
    private void refreshCartTable() {
        cartTableModel.setRowCount(0);
        ShoppingCart cart = currentCustomer.getCart();
        for (ShoppingCart.CartItem item : cart.getItems()) {
            cartTableModel.addRow(new Object[]{
                item.getProduct().getName(),
                item.getQuantity(),
                String.format("$%.2f", item.getProduct().getPrice()),
                String.format("$%.2f", item.getSubtotal())
            });
        }
        updateCartInfo();
    }
    
    /**
     * Updates the cart information labels.
     */
    private void updateCartInfo() {
        ShoppingCart cart = currentCustomer.getCart();
        cartCountLabel.setText("Items: " + cart.getTotalItemCount());
        totalLabel.setText(String.format("Total: $%.2f", cart.calculateTotal()));
    }
    
    /**
     * Adds the selected product to the shopping cart.
     */
    private void addSelectedProductToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a product from the list.", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int productID = (int) productTableModel.getValueAt(selectedRow, 0);
        Product product = findProduct(productID);
        
        if (product == null) {
            JOptionPane.showMessageDialog(this, 
                "Product not found.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get quantity from spinner
        int quantity = (int) qtySpinner.getValue();
        
        // Check stock
        if (quantity > product.getStockQuantity()) {
            JOptionPane.showMessageDialog(this, 
                "Insufficient stock! Available: " + product.getStockQuantity(), 
                "Stock Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Add to cart
        try {
            currentCustomer.addToCart(product, quantity);
            // Reduce stock
            product.reduceStock(quantity);
            refreshProductTable();
            refreshCartTable();
            JOptionPane.showMessageDialog(this, 
                quantity + " x " + product.getName() + " added to cart.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error adding to cart: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Removes the selected item from the shopping cart.
     */
    private void removeSelectedFromCart() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an item from your cart.", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String productName = (String) cartTableModel.getValueAt(selectedRow, 0);
        ShoppingCart cart = currentCustomer.getCart();
        
        // Find and remove the product
        for (ShoppingCart.CartItem item : cart.getItems()) {
            if (item.getProduct().getName().equals(productName)) {
                // Restore stock
                Product product = findProduct(item.getProduct().getProductID());
                if (product != null) {
                    product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                }
                cart.removeItem(item.getProduct().getProductID());
                break;
            }
        }
        
        refreshProductTable();
        refreshCartTable();
        JOptionPane.showMessageDialog(this, 
            "Item removed from cart.", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Clears all items from the shopping cart.
     */
    private void clearCart() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear your cart?", 
            "Confirm Clear", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Restore stock for all items
            ShoppingCart cart = currentCustomer.getCart();
            for (ShoppingCart.CartItem item : cart.getItems()) {
                Product product = findProduct(item.getProduct().getProductID());
                if (product != null) {
                    product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                }
            }
            cart.clear();
            refreshProductTable();
            refreshCartTable();
            JOptionPane.showMessageDialog(this, 
                "Cart cleared.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Places an order using the current cart contents.
     */
    private void placeOrder() {
        ShoppingCart cart = currentCustomer.getCart();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Your cart is empty! Add some products first.", 
                "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Order order = currentCustomer.placeOrder(nextOrderID++);
            orderSummaryArea.setText(order.generateOrderSummary());
            currentOrderLabel.setText("Order #" + order.getOrderID());
            // Select the current status in the combo box
            statusComboBox.setSelectedItem(order.getStatus().getDisplayName());
            
            refreshCartTable();
            JOptionPane.showMessageDialog(this, 
                "Order placed successfully!\nOrder ID: " + order.getOrderID(), 
                "Order Placed", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error placing order: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Updates the order status when the combo box selection changes.
     */
    private void updateOrderStatus() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        if (selectedStatus != null && !selectedStatus.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Order status updated to: " + selectedStatus, 
                "Status Update", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Finds a product by its ID.
     * 
     * @param productID The product ID to search for
     * @return The Product object, or null if not found
     */
    private Product findProduct(int productID) {
        for (Product p : products) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * The main method launches the application.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Use default look and feel
            }
            new ECommerceGUI().setVisible(true);
        });
    }
}