import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * ChatClient - a Swing-based GUI client for the chat application.
 * Connects to the server, sends messages, and displays incoming messages.
 */
public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    // GUI components
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    // Server connection details
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ChatClient().start();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Failed to connect to server: " + e.getMessage(),
                        "Connection Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    /**
     * Initialise the GUI and establish connection.
     */
    private void start() throws IOException {
        // Connect to server
        socket = new Socket(SERVER_HOST, SERVER_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Build the GUI
        buildGUI();

        // Start the reader thread to receive messages
        new Thread(this::readMessages).start();
    }

    /**
     * Create and show the main window.
     */
    private void buildGUI() {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel: input field and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.addActionListener(e -> sendMessage());

        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Set window properties
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Focus on input field
        inputField.requestFocus();
    }

    /**
     * Send the message typed in the input field to the server.
     */
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty() && out != null) {
            out.println(message);
            inputField.setText("");
            inputField.requestFocus();
        }
    }

    /**
     * Continuously read incoming messages from the server and update the GUI.
     */
    private void readMessages() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                final String msg = line;
                SwingUtilities.invokeLater(() -> {
                    chatArea.append(msg + "\n");
                    // Auto-scroll to the latest message
                    chatArea.setCaretPosition(chatArea.getDocument().getLength());
                });
            }
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(frame,
                        "Connection lost to server: " + e.getMessage(),
                        "Disconnected", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            });
        }
    }
}