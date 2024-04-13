import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/***
 * Esta classe lida com cada cliente conectado ao servidor. Ela é usada pelo
 * servidor para gerenciar conexões individuais de clientes em threads
 * separadas.
 */
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
            System.out.println("Servidor iniciado. Aguardando conexões na porta " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Novo usuário conectado");

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
            if (aUser.getNickname() == null) {
                continue;
            }

            if (aUser.getNickname().equals(nickname)) {
                aUser.sendMessage(message);
                break;
            }
        }
    }

    void removeUser(ClientThread user, String userName) {
        boolean removed = clients.remove(user);
        if (removed) {
            System.out.println("O usuário " + userName + " desconectou e foi removido da lista.");
            broadcast(userName + " has left the chat.", null);
        }
    }

    String getConnectedClients() {
        StringBuilder sb = new StringBuilder("Clientes conectados: ");
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

    public Boolean isNicknameTaken(String nickname) {
        for (ClientThread aUser : clients) {
            if (aUser.getNickname() == null) {
                continue;
            }

            if (aUser.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
}
