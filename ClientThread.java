import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private String nickname;

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            // Primeira mensagem recebida deve ser o nick do usuário
            String nickName = reader.readLine();
            this.nickname = nickName;
            server.broadcast(nickName + " has joined the chat", this);

            String serverMessage;
            String clientMessage;

            do {
                clientMessage = reader.readLine();
                if (clientMessage.startsWith("/private")) {
                    // Formato: /private <nick> <message>
                    String[] parts = clientMessage.split(" ", 3);
                    if (parts.length == 3) {
                        server.sendToSpecificClient("[" + nickName + " -> you]: " + parts[2], parts[1]);
                    }
                } else if (clientMessage.equals("/list")) {
                    sendMessage(server.getConnectedClients());
                } else {
                    serverMessage = "[" + nickName + "]: " + clientMessage;
                    server.broadcast(serverMessage, this);
                }

            } while (!clientMessage.equals("/quit"));

            server.removeUser(this, nickName);
            socket.close();

        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }

    public String getNickname() {
        return nickname;
    }
}
