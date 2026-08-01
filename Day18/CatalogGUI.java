import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

/**
 * Swing GUI for the generic library catalog.
 * Allows adding, removing, and viewing items of three types.
 */
public class CatalogGUI extends JFrame {
    // Catalogs for each item type
    private final Catalog<BookDetails> bookCatalog = new Catalog<>();
    private final Catalog<DVDDetails> dvdCatalog = new Catalog<>();
    private final Catalog<MagazineDetails> magazineCatalog = new Catalog<>();

    // UI components
    private JComboBox<String> typeCombo;
    private JTextField titleField, authorField, idField;
    private JPanel detailsPanel;
    private JTextField pagesField, publisherField;
    private JTextField directorField, durationField;
    private JTextField issueField, magPublisherField;
    private JTextArea displayArea;

    public CatalogGUI() {
        setTitle("Generic Library Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Top panel: type selection and common fields
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Item Information"));

        topPanel.add(new JLabel("Item Type:"));
        typeCombo = new JComboBox<>(new String[]{"Book", "DVD", "Magazine"});
        typeCombo.addActionListener(e -> updateDetailsPanel());
        topPanel.add(typeCombo);

        topPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        topPanel.add(titleField);

        topPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        topPanel.add(authorField);

        topPanel.add(new JLabel("Item ID:"));
        idField = new JTextField();
        topPanel.add(idField);

        add(topPanel, BorderLayout.NORTH);

        // Center panel: type-specific details
        detailsPanel = new JPanel(new CardLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Type-Specific Details"));

        // Book details panel
        JPanel bookPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        bookPanel.add(new JLabel("Pages:"));
        pagesField = new JTextField();
        bookPanel.add(pagesField);
        bookPanel.add(new JLabel("Publisher:"));
        publisherField = new JTextField();
        bookPanel.add(publisherField);
        detailsPanel.add(bookPanel, "Book");

        // DVD details panel
        JPanel dvdPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        dvdPanel.add(new JLabel("Director:"));
        directorField = new JTextField();
        dvdPanel.add(directorField);
        dvdPanel.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        dvdPanel.add(durationField);
        detailsPanel.add(dvdPanel, "DVD");

        // Magazine details panel
        JPanel magPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        magPanel.add(new JLabel("Issue Number:"));
        issueField = new JTextField();
        magPanel.add(issueField);
        magPanel.add(new JLabel("Publisher:"));
        magPublisherField = new JTextField();
        magPanel.add(magPublisherField);
        detailsPanel.add(magPanel, "Magazine");

        add(detailsPanel, BorderLayout.CENTER);

        // Bottom: action buttons and display area
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(this::addItem);
        JButton removeButton = new JButton("Remove Item");
        removeButton.addActionListener(this::removeItem);
        JButton viewButton = new JButton("View All Items");
        viewButton.addActionListener(this::viewItems);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Catalog Contents"));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Show book details by default
        updateDetailsPanel();
    }

    private void updateDetailsPanel() {
        CardLayout cl = (CardLayout) detailsPanel.getLayout();
        cl.show(detailsPanel, (String) typeCombo.getSelectedItem());
    }

    // ---------- Action Handlers ----------

    private void addItem(ActionEvent e) {
        String type = (String) typeCombo.getSelectedItem();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String id = idField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title, Author and ID are required.");
            return;
        }

        try {
            switch (type) {
                case "Book":
                    int pages = Integer.parseInt(pagesField.getText().trim());
                    String pub = publisherField.getText().trim();
                    BookDetails bDetails = new BookDetails(pages, pub);
                    LibraryItem<BookDetails> bookItem = new LibraryItem<>(title, author, id, bDetails);
                    bookCatalog.addItem(bookItem);
                    break;
                case "DVD":
                    String director = directorField.getText().trim();
                    int duration = Integer.parseInt(durationField.getText().trim());
                    DVDDetails dDetails = new DVDDetails(director, duration);
                    LibraryItem<DVDDetails> dvdItem = new LibraryItem<>(title, author, id, dDetails);
                    dvdCatalog.addItem(dvdItem);
                    break;
                case "Magazine":
                    int issue = Integer.parseInt(issueField.getText().trim());
                    String magPub = magPublisherField.getText().trim();
                    MagazineDetails mDetails = new MagazineDetails(issue, magPub);
                    LibraryItem<MagazineDetails> magItem = new LibraryItem<>(title, author, id, mDetails);
                    magazineCatalog.addItem(magItem);
                    break;
            }
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            clearFields();
            viewItems(null); // refresh display
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please check numeric fields.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void removeItem(ActionEvent e) {
        String id = JOptionPane.showInputDialog(this, "Enter Item ID to remove:");
        if (id == null || id.trim().isEmpty()) return;

        String type = (String) typeCombo.getSelectedItem();
        try {
            switch (type) {
                case "Book":
                    bookCatalog.removeItem(id.trim());
                    break;
                case "DVD":
                    dvdCatalog.removeItem(id.trim());
                    break;
                case "Magazine":
                    magazineCatalog.removeItem(id.trim());
                    break;
            }
            JOptionPane.showMessageDialog(this, "Item removed.");
            viewItems(null);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewItems(ActionEvent e) {
        String type = (String) typeCombo.getSelectedItem();
        List<? extends LibraryItem<?>> items;
        switch (type) {
            case "Book": items = bookCatalog.getAllItems(); break;
            case "DVD": items = dvdCatalog.getAllItems(); break;
            case "Magazine": items = magazineCatalog.getAllItems(); break;
            default: return;
        }

        if (items.isEmpty()) {
            displayArea.setText("No items in the catalog.");
        } else {
            StringBuilder sb = new StringBuilder("=== " + type + " Catalog ===\n");
            for (LibraryItem<?> item : items) {
                sb.append(item).append("\n");
            }
            displayArea.setText(sb.toString());
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        idField.setText("");
        pagesField.setText("");
        publisherField.setText("");
        directorField.setText("");
        durationField.setText("");
        issueField.setText("");
        magPublisherField.setText("");
    }

    // ---------- Main ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CatalogGUI::new);
    }
}