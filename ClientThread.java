/**
 * Tarefa que gerencia a comunicação entre o servidor e um cliente específico no chat. 
 * Cada instância desta classe é responsável por lidar com as mensagens de entrada e saída de um único cliente.
 *
 * Funcionalidades:
 * - Receber mensagens de um cliente através do socket e processá-las de acordo com o
 *   comando especificado (por exemplo, envio de mensagem privada, listagem de usuários conectados).
 * - Enviar mensagens para o cliente associado a esta thread, seja uma mensagem de chat geral,
 *   uma mensagem privada, ou a lista de usuários conectados.
 * - Gerenciar a conexão do cliente, incluindo o registro do seu nickname e a remoção do
 *   cliente da lista de usuários conectados quando o comando "/quit" é recebido.
 */
public class ClientThread implements Runnable {
    
}
