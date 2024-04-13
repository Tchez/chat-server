import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private int port;
    private ArrayList<ClientThread> clients;
    private ExecutorService pool;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
        pool = Executors.newCachedThreadPool();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                ClientThread newUser = new ClientThread(socket, this);
                clients.add(newUser);
                pool.execute(newUser);
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void broadcast(String message, ClientThread excludeUser) {
        for (ClientThread aUser : clients) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    void sendToSpecificClient(String message, String nickname) {
        for (ClientThread aUser : clients) {
            if (aUser.getNickname().equals(nickname)) {
                aUser.sendMessage(message);
                break;
            }
        }
    }

    void removeUser(ClientThread user, String userName) {
        boolean removed = clients.remove(user);
        if (removed) {
            System.out.println("The user: " + userName + " disconnected and was removed from the list.");
            broadcast(userName + " has left the chat.", null);
        }
    }

    String getConnectedClients() {
        StringBuilder sb = new StringBuilder("Connected clients: ");
        for (ClientThread aUser : clients) {
            sb.append(aUser.getNickname()).append(", ");
        }
        return sb.toString().replaceAll(", $", "");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java Server <port-number>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server server = new Server(port);
        server.execute();
    }
}
