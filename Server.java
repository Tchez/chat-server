/**
 * Servidor para o chat
 * Ela espera conexões de clientes, gerencia mensagens de chat e mantém uma lista de usuários conectados. 
 * Cada cliente é tratado em sua própria thread.
 *
 * Funcionalidades:
 * - Escutar por conexões de clientes em uma porta específica.
 * - Aceitar novas conexões de cliente e iniciar uma nova thread para cada uma delas.
 * - Transmitir mensagens recebidas de um cliente para todos os outros clientes conectados,
 *   exceto para o cliente que enviou a mensagem.
 * - Permitir enviar mensagens para um cliente específico (mensagens privadas).
 * - Manter uma lista de clientes conectados e fornecer um método para obter essa lista.
 * - Remover um cliente da lista quando ele se desconecta.
 */
public class Server {
    
}
