import java.io.*;
import java.net.*;
import java.util.*;

/**
 * ChatServer - a multithreaded chat server that accepts clients,
 * assigns unique IDs, and broadcasts messages to all connected users.
 */
public class ChatServer {
    private static final int PORT = 12345;
    // Thread-safe list of all client handlers
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static int nextId = 1;

    public static void main(String[] args) {
        System.out.println("ChatServer started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                int userId = nextId++;
                System.out.println("New client connected: User " + userId);
                ClientHandler handler = new ClientHandler(clientSocket, userId);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    /**
     * Broadcast a message to all connected clients.
     * @param message the message to send
     */
    public static void broadcast(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    /**
     * Remove a client from the active list and notify others.
     * @param handler the client to remove
     */
    public static void removeClient(ClientHandler handler) {
        clients.remove(handler);
        broadcast("[System] User " + handler.getUserId() + " has left the chat.");
        System.out.println("User " + handler.getUserId() + " disconnected. Active clients: " + clients.size());
    }

    /**
     * Inner class that handles communication with one client.
     */
    static class ClientHandler implements Runnable {
        private final Socket socket;
        private final int userId;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket, int userId) {
            this.socket = socket;
            this.userId = userId;
        }

        public int getUserId() {
            return userId;
        }

        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
                out.flush();
            }
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Notify this client of its ID
                out.println("[System] You are User " + userId);

                // Broadcast join message
                broadcast("[System] User " + userId + " has joined the chat.");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    // Broadcast the message with sender identification
                    broadcast("[User " + userId + "] " + inputLine);
                    System.out.println("User " + userId + ": " + inputLine);
                }
            } catch (IOException e) {
                System.err.println("Error with User " + userId + ": " + e.getMessage());
            } finally {
                // Clean up when the client disconnects
                try {
                    if (socket != null) socket.close();
                } catch (IOException ignored) {}
                removeClient(this);
            }
        }
    }
}