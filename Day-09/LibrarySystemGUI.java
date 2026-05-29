import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Library Management System – GUI with transaction history.
 * Features: Add, Borrow, Return, Delete, Search/Sort, Persistent storage,
 * and full borrow/return record keeping.
 */
public class LibrarySystemGUI extends JFrame {
    private LibraryManager libraryManager;
    private TransactionManager transactionManager;
    
    // Inventory tab components
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTextField searchField;
    private JTextField titleField, authorField, quantityField;
    
    // History tab components
    private JTable historyTable;
    private DefaultTableModel historyModel;
    
    private JLabel statusLabel;
    
    public LibrarySystemGUI() {
        libraryManager = new LibraryManager();
        libraryManager.loadFromFile();
        transactionManager = new TransactionManager();
        transactionManager.loadFromFile();
        
        initUI();
        refreshTable();
        refreshHistory();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                libraryManager.saveToFile();
                transactionManager.saveToFile();
            }
        });
    }
    
    private void initUI() {
        setTitle("Library Management System with Transaction History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(1000, 650));
        
        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // ========== INVENTORY TAB ==========
        JPanel inventoryPanel = createInventoryPanel();
        tabbedPane.addTab("Book Inventory", inventoryPanel);
        
        // ========== HISTORY TAB ==========
        JPanel historyPanel = createHistoryPanel();
        tabbedPane.addTab("Transaction History", historyPanel);
        
        // Menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        // Status bar
        statusLabel = new JLabel(" ");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Top: Add Book
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addPanel.setBorder(BorderFactory.createTitledBorder("Add New Book"));
        addPanel.add(new JLabel("Title:"));
        titleField = new JTextField(15);
        addPanel.add(titleField);
        addPanel.add(new JLabel("Author:"));
        authorField = new JTextField(15);
        addPanel.add(authorField);
        addPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField(5);
        addPanel.add(quantityField);
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> addBook());
        addPanel.add(addButton);
        
        // Center: Table with search
        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Quantity"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookTable.getTableHeader().setReorderingAllowed(false);
        rowSorter = new TableRowSorter<>(tableModel);
        bookTable.setRowSorter(rowSorter);
        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Book Inventory"));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search (Title/Author):"));
        searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });
        searchPanel.add(searchField);
        JButton clearSearch = new JButton("Clear");
        clearSearch.addActionListener(e -> {
            searchField.setText("");
            filter();
        });
        searchPanel.add(clearSearch);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Bottom: Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        
        JButton borrowButton = new JButton("Borrow Selected Book");
        borrowButton.addActionListener(e -> borrowBook());
        actionPanel.add(borrowButton);
        
        JButton returnButton = new JButton("Return Selected Book");
        returnButton.addActionListener(e -> returnBook());
        actionPanel.add(returnButton);
        
        JButton deleteButton = new JButton("Delete Selected Book");
        deleteButton.addActionListener(e -> deleteBook());
        actionPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> refreshTable());
        actionPanel.add(refreshButton);
        
        panel.add(addPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        historyModel = new DefaultTableModel(new String[]{"Timestamp", "Type", "Title", "Author", "Quantity"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        historyTable = new JTable(historyModel);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Borrow/Return Records"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clearHistoryButton = new JButton("Clear All History");
        clearHistoryButton.addActionListener(e -> clearHistory());
        buttonPanel.add(clearHistoryButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save Library & History");
        saveItem.addActionListener(e -> {
            libraryManager.saveToFile();
            transactionManager.saveToFile();
            setStatus("Library and transaction history saved.");
        });
        JMenuItem loadItem = new JMenuItem("Load Library & History");
        loadItem.addActionListener(e -> {
            libraryManager.loadFromFile();
            transactionManager.loadFromFile();
            refreshTable();
            refreshHistory();
            setStatus("Data reloaded from disk.");
        });
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            libraryManager.saveToFile();
            transactionManager.saveToFile();
            System.exit(0);
        });
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Library Management System\n\nFeatures:\n- Add / Borrow / Return / Delete\n- Persistent storage\n- Transaction history with timestamps\n- Search & sort",
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }
    
    private void filter() {
        String text = searchField.getText().trim();
        if (text.isEmpty()) rowSorter.setRowFilter(null);
        else rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Book book : libraryManager.getAllBooks()) {
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getQuantity()});
        }
        setStatus("Inventory updated. Total unique books: " + libraryManager.getTotalUniqueBooks());
    }
    
    private void refreshHistory() {
        historyModel.setRowCount(0);
        for (Transaction t : transactionManager.getAllTransactions()) {
            historyModel.addRow(new Object[]{
                t.getTimestamp(), t.getType(), t.getBookTitle(), t.getBookAuthor(), t.getQuantity()
            });
        }
        // Optional: auto-scroll to bottom
        if (historyModel.getRowCount() > 0) {
            historyTable.scrollRectToVisible(historyTable.getCellRect(historyModel.getRowCount()-1, 0, true));
        }
    }
    
    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String qtyStr = quantityField.getText().trim();
        if (title.isEmpty() || author.isEmpty() || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a positive integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean added = libraryManager.addBook(title, author, qty);
        if (added) {
            refreshTable();
            titleField.setText("");
            authorField.setText("");
            quantityField.setText("");
            setStatus("Added/updated '" + title + "' by " + author + " (+" + qty + " copies).");
        } else {
            setStatus("Failed to add book.");
        }
    }
    
    private void borrowBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to borrow.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = bookTable.convertRowIndexToModel(selectedRow);
        String title = (String) tableModel.getValueAt(modelRow, 0);
        String author = (String) tableModel.getValueAt(modelRow, 1);
        Book book = libraryManager.getBook(title, author);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String qtyStr = JOptionPane.showInputDialog(this, "Number of copies to borrow (available: " + book.getQuantity() + "):",
                "Borrow", JOptionPane.QUESTION_MESSAGE);
        if (qtyStr == null) return;
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a positive integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String result = libraryManager.borrowBook(book, qty);
        if (result.startsWith("Success")) {
            // Record transaction
            transactionManager.addTransaction(new Transaction("Borrow", title, author, qty));
            refreshTable();
            refreshHistory();
            setStatus(result);
        } else {
            JOptionPane.showMessageDialog(this, result, "Borrow Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void returnBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to return.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = bookTable.convertRowIndexToModel(selectedRow);
        String title = (String) tableModel.getValueAt(modelRow, 0);
        String author = (String) tableModel.getValueAt(modelRow, 1);
        Book book = libraryManager.getBook(title, author);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String qtyStr = JOptionPane.showInputDialog(this, "Number of copies to return:",
                "Return", JOptionPane.QUESTION_MESSAGE);
        if (qtyStr == null) return;
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a positive integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String result = libraryManager.returnBook(book, qty);
        if (result.startsWith("Success")) {
            transactionManager.addTransaction(new Transaction("Return", title, author, qty));
            refreshTable();
            refreshHistory();
            setStatus(result);
        } else {
            JOptionPane.showMessageDialog(this, result, "Return Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = bookTable.convertRowIndexToModel(selectedRow);
        String title = (String) tableModel.getValueAt(modelRow, 0);
        String author = (String) tableModel.getValueAt(modelRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete \"" + title + "\" by " + author + "?\nThis will also remove all copies.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean removed = libraryManager.removeBook(title, author);
            if (removed) {
                refreshTable();
                setStatus("Deleted book: " + title);
            } else {
                setStatus("Delete failed.");
            }
        }
    }
    
    private void clearHistory() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Clear all transaction history? This cannot be undone.",
                "Clear History", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            transactionManager.clearAll();
            refreshHistory();
            setStatus("Transaction history cleared.");
        }
    }
    
    private void setStatus(String msg) {
        statusLabel.setText(" " + msg);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySystemGUI());
    }
}

/**
 * Book class – unchanged.
 */
class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title, author;
    private int quantity;
    public Book(String title, String author, int quantity) {
        this.title = title; this.author = author; this.quantity = quantity;
    }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getKey() { return title.trim().toLowerCase() + "|" + author.trim().toLowerCase(); }
}

/**
 * Transaction record – timestamp, type, book details, quantity.
 */
class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String timestamp;
    private String type;        // "Borrow" or "Return"
    private String bookTitle;
    private String bookAuthor;
    private int quantity;
    
    public Transaction(String type, String bookTitle, String bookAuthor, int quantity) {
        this.timestamp = LocalDateTime.now().format(FORMATTER);
        this.type = type;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.quantity = quantity;
    }
    
    public String getTimestamp() { return timestamp; }
    public String getType() { return type; }
    public String getBookTitle() { return bookTitle; }
    public String getBookAuthor() { return bookAuthor; }
    public int getQuantity() { return quantity; }
}

/**
 * TransactionManager – stores and persists transaction history.
 */
class TransactionManager {
    private List<Transaction> transactions;
    private static final String HISTORY_FILE = "transactions.ser";
    
    public TransactionManager() {
        transactions = new ArrayList<>();
    }
    
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }
    
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    public void clearAll() {
        transactions.clear();
    }
    
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            transactions = (List<Transaction>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load transaction history: " + e.getMessage());
        }
    }
    
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            System.err.println("Could not save transaction history: " + e.getMessage());
        }
    }
}

/**
 * LibraryManager – updated to work with Book class.
 */
class LibraryManager {
    private Map<String, Book> library;
    private static final String DATA_FILE = "library_data.ser";
    
    public LibraryManager() { library = new HashMap<>(); }
    
    public boolean addBook(String title, String author, int quantity) {
        if (quantity <= 0) return false;
        String key = new Book(title, author, 0).getKey();
        Book existing = library.get(key);
        if (existing != null) existing.setQuantity(existing.getQuantity() + quantity);
        else library.put(key, new Book(title, author, quantity));
        return true;
    }
    
    public String borrowBook(Book book, int quantity) {
        if (book == null) return "Book not found.";
        if (quantity <= 0) return "Quantity must be positive.";
        if (book.getQuantity() < quantity) return "Insufficient copies. Only " + book.getQuantity() + " available.";
        book.setQuantity(book.getQuantity() - quantity);
        return "Success: Borrowed " + quantity + " copy/copies of '" + book.getTitle() + "'. Remaining: " + book.getQuantity();
    }
    
    public String returnBook(Book book, int quantity) {
        if (book == null) return "Book not found.";
        if (quantity <= 0) return "Quantity must be positive.";
        book.setQuantity(book.getQuantity() + quantity);
        return "Success: Returned " + quantity + " copy/copies of '" + book.getTitle() + "'. New stock: " + book.getQuantity();
    }
    
    public Book getBook(String title, String author) {
        String key = new Book(title, author, 0).getKey();
        return library.get(key);
    }
    
    public boolean removeBook(String title, String author) {
        String key = new Book(title, author, 0).getKey();
        return library.remove(key) != null;
    }
    
    public Collection<Book> getAllBooks() { return library.values(); }
    public int getTotalUniqueBooks() { return library.size(); }
    
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            library = (Map<String, Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) { /* ignore */ }
    }
    
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(library);
        } catch (IOException e) { /* ignore */ }
    }
}