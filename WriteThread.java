/**
 * Responsável por enviar mensagens de um cliente para o servidor no chat. 
 * Thread, fluxo de execução separado, sem bloquear a leitura de mensagens vindas do servidor.
 *
 * Funcionalidades:
 * - Capturar mensagens digitadas pelo usuário no console e enviá-las ao servidor.
 * - Permitir que o usuário defina um nickname ao se conectar ao chat, o qual é enviado ao
 *   servidor antes de qualquer outra mensagem.
 * - Continuar a enviar mensagens digitadas pelo usuário até que o comando "/quit" seja
 *   digitado, indicando o desejo de se desconectar.
 * - Fechar a conexão do socket quando o usuário decide sair do chat.
 */
public class WriteThread extends Thread {
    
}
