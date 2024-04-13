import java.io.*;
import java.net.*;

/***
 * Esta classe lida com cada cliente conectado ao servidor. Ela é usada pelo
 * servidor para gerenciar conexões individuais de clientes em threads
 * separadas.
 */
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

            if (nickName == null || nickName.isEmpty()) {
                throw new ConnectException("Nick não pode ser vazio");
            } else if (server.isNicknameTaken(nickName)) {
                throw new ConnectException("Nick já está em uso");
            }

            this.nickname = nickName;

            writer.println("Bem-vindo ao chat, " + nickName + "!" + " Para sair digite /quit");
            server.broadcast(nickName + " entrou no chat", this);

            String serverMessage;
            String clientMessage;

            do {
                clientMessage = reader.readLine();
                if (clientMessage.startsWith("/private")) {
                    // Formato: /private <nick> <message>
                    String[] parts = clientMessage.split(" ", 3);
                    if (parts.length == 3) {
                        server.sendToSpecificClient("[" + nickName + " -> você]: " + parts[2], parts[1]);
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

        } catch (Exception ex) {
            if (ex instanceof ConnectException) {
                System.out.println("Usuário tentou se conectar, mas falhou: " + ex.getMessage());
                writer.println(ex.getMessage());
            } else {
                System.out.println("Error in UserThread: " + ex.getMessage());
                writer.println("Erro ao se conectar ao servidor. Tente novamente mais tarde.");
            }
            // ex.printStackTrace(); // Descomentar para ajudar no debug
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }

    public String getNickname() {
        return nickname;
    }
}
