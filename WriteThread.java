import java.io.*;
import java.net.*;
import java.util.Scanner;

/***
 * Escreve as mensagens do cliente no servidor.
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite seu nickname: ");
        String userName = scanner.nextLine();
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do {
            text = scanner.nextLine();
            writer.println(text);
        } while (!text.equals("/quit"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
