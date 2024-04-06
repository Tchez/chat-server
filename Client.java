import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu nome: ");
        String user = scanner.nextLine();

        if (user.isEmpty()) {
            System.out.println("Nome de usuário inválido");
            return;
        }

        System.out.println("Bem vindo " + user);
        System.out.println("Inicializando conexão com o servidor...");

        try {
            String opcao;
            String resposta;

            Socket socket = new Socket("localhost", 3333);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Scanner entrada = new Scanner(System.in);
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);

            writer.println(user);
            writer.flush();

            do {
                System.out.print("Selecione uma opção: ");
                System.out.println("1 - Listar usuários");
                System.out.println("2 - Enviar mensagem");
                System.out.println("3 - Sair");
                System.out.print("Opção: ");

                opcao = entrada.nextLine();
                writer.println(opcao);
                writer.flush();
                resposta = bufferedReader.readLine();
                System.out.println(resposta);
            } while (!opcao.equals("3"));

            bufferedReader.close();
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Exceção..: " + e.getMessage());
        }
    }
}
