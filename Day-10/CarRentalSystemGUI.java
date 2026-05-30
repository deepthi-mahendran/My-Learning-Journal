import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class CarRentalSystemGUI extends JFrame {
    private List<Vehicle> vehicles;
    private JTextArea displayArea;

    // Car specific fields
    private JTextField carMakeField, carModelField, carYearField;
    private JComboBox<Integer> carDoorsCombo;
    private JComboBox<String> carFuelCombo;

    // Motorcycle specific fields
    private JTextField motoMakeField, motoModelField, motoYearField;
    private JComboBox<Integer> motoWheelsCombo;
    private JComboBox<String> motoTypeCombo;

    // Truck specific fields
    private JTextField truckMakeField, truckModelField, truckYearField;
    private JTextField truckCargoField;
    private JComboBox<String> truckTransCombo;

    public CarRentalSystemGUI() {
        vehicles = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Car Rental Agency System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 550);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Car", createAddCarPanel());
        tabbedPane.addTab("Add Motorcycle", createAddMotorcyclePanel());
        tabbedPane.addTab("Add Truck", createAddTruckPanel());
        tabbedPane.addTab("Display All Vehicles", createDisplayPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createAddCarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Make
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Make:"), gbc);
        gbc.gridx = 1;
        carMakeField = new JTextField(15);
        panel.add(carMakeField, gbc);

        // Model
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1;
        carModelField = new JTextField(15);
        panel.add(carModelField, gbc);

        // Year
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Year (1885-current):"), gbc);
        gbc.gridx = 1;
        carYearField = new JTextField(15);
        panel.add(carYearField, gbc);

        // Doors
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Number of Doors (2-5):"), gbc);
        gbc.gridx = 1;
        Integer[] doorOptions = {2, 3, 4, 5};
        carDoorsCombo = new JComboBox<>(doorOptions);
        panel.add(carDoorsCombo, gbc);

        // Fuel Type
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Fuel Type:"), gbc);
        gbc.gridx = 1;
        String[] fuelOptions = {"Petrol", "Diesel", "Electric"};
        carFuelCombo = new JComboBox<>(fuelOptions);
        panel.add(carFuelCombo, gbc);

        // Add Button
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Car");
        addButton.addActionListener(e -> addCar());
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createAddMotorcyclePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Make
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Make:"), gbc);
        gbc.gridx = 1;
        motoMakeField = new JTextField(15);
        panel.add(motoMakeField, gbc);

        // Model
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1;
        motoModelField = new JTextField(15);
        panel.add(motoModelField, gbc);

        // Year
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Year (1885-current):"), gbc);
        gbc.gridx = 1;
        motoYearField = new JTextField(15);
        panel.add(motoYearField, gbc);

        // Wheels
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Number of Wheels (2 or 3):"), gbc);
        gbc.gridx = 1;
        Integer[] wheelOptions = {2, 3};
        motoWheelsCombo = new JComboBox<>(wheelOptions);
        panel.add(motoWheelsCombo, gbc);

        // Motorcycle Type
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Motorcycle Type:"), gbc);
        gbc.gridx = 1;
        String[] typeOptions = {"Sport", "Cruiser", "Off-Road"};
        motoTypeCombo = new JComboBox<>(typeOptions);
        panel.add(motoTypeCombo, gbc);

        // Add Button
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Motorcycle");
        addButton.addActionListener(e -> addMotorcycle());
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createAddTruckPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Make
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Make:"), gbc);
        gbc.gridx = 1;
        truckMakeField = new JTextField(15);
        panel.add(truckMakeField, gbc);

        // Model
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1;
        truckModelField = new JTextField(15);
        panel.add(truckModelField, gbc);

        // Year
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Year (1885-current):"), gbc);
        gbc.gridx = 1;
        truckYearField = new JTextField(15);
        panel.add(truckYearField, gbc);

        // Cargo Capacity
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Cargo Capacity (tons >0):"), gbc);
        gbc.gridx = 1;
        truckCargoField = new JTextField(15);
        panel.add(truckCargoField, gbc);

        // Transmission
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Transmission Type:"), gbc);
        gbc.gridx = 1;
        String[] transOptions = {"Manual", "Automatic"};
        truckTransCombo = new JComboBox<>(transOptions);
        panel.add(truckTransCombo, gbc);

        // Add Button
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Truck");
        addButton.addActionListener(e -> addTruck());
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Display");
        refreshButton.addActionListener(e -> refreshDisplay());
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }

    private void addCar() {
        try {
            String make = validateNonEmpty(carMakeField.getText(), "Make");
            String model = validateNonEmpty(carModelField.getText(), "Model");
            int year = validateYear(carYearField.getText());
            int doors = (int) carDoorsCombo.getSelectedItem();
            String fuel = (String) carFuelCombo.getSelectedItem();

            Car car = new Car(make, model, year, doors, fuel);
            vehicles.add(car);
            clearCarFields();
            JOptionPane.showMessageDialog(this, "Car added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMotorcycle() {
        try {
            String make = validateNonEmpty(motoMakeField.getText(), "Make");
            String model = validateNonEmpty(motoModelField.getText(), "Model");
            int year = validateYear(motoYearField.getText());
            int wheels = (int) motoWheelsCombo.getSelectedItem();
            String type = (String) motoTypeCombo.getSelectedItem();

            Motorcycle motorcycle = new Motorcycle(make, model, year, wheels, type);
            vehicles.add(motorcycle);
            clearMotorcycleFields();
            JOptionPane.showMessageDialog(this, "Motorcycle added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTruck() {
        try {
            String make = validateNonEmpty(truckMakeField.getText(), "Make");
            String model = validateNonEmpty(truckModelField.getText(), "Model");
            int year = validateYear(truckYearField.getText());
            double capacity = validateCargoCapacity(truckCargoField.getText());
            String transmission = (String) truckTransCombo.getSelectedItem();

            Truck truck = new Truck(make, model, year, capacity, transmission);
            vehicles.add(truck);
            clearTruckFields();
            JOptionPane.showMessageDialog(this, "Truck added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshDisplay() {
        if (vehicles.isEmpty()) {
            displayArea.setText("No vehicles in inventory yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("========== COMPLETE VEHICLE INVENTORY ==========\n\n");
        for (int i = 0; i < vehicles.size(); i++) {
            sb.append("Vehicle #").append(i + 1).append("\n");
            sb.append(vehicles.get(i).toString()).append("\n");
            sb.append("----------------------------------------\n\n");
        }
        displayArea.setText(sb.toString());
    }

    // Validation helpers
    private String validateNonEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return input.trim();
    }

    private int validateYear(String yearStr) {
        try {
            int year = Integer.parseInt(yearStr.trim());
            int currentYear = Year.now().getValue();
            if (year < 1885 || year > currentYear) {
                throw new IllegalArgumentException("Year must be between 1885 and " + currentYear);
            }
            return year;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year must be a valid number.");
        }
    }

    private double validateCargoCapacity(String capacityStr) {
        try {
            double capacity = Double.parseDouble(capacityStr.trim());
            if (capacity <= 0) {
                throw new IllegalArgumentException("Cargo capacity must be a positive number.");
            }
            return capacity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cargo capacity must be a valid number.");
        }
    }

    private void clearCarFields() {
        carMakeField.setText("");
        carModelField.setText("");
        carYearField.setText("");
    }

    private void clearMotorcycleFields() {
        motoMakeField.setText("");
        motoModelField.setText("");
        motoYearField.setText("");
    }

    private void clearTruckFields() {
        truckMakeField.setText("");
        truckModelField.setText("");
        truckYearField.setText("");
        truckCargoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarRentalSystemGUI());
    }
}