# Sistema de Chat

## Visão Geral

Este projeto visa a implementação de um sistema de chat que permite a comunicação em tempo real entre múltiplos clientes através de um servidor concorrente. O servidor gerencia as conexões, as mensagens e mantém uma lista de clientes ativos. Os clientes podem enviar mensagens tanto para todos os usuários quanto para usuários específicos.

## Componentes do Sistema

- **Servidor Concorrente**: Gerencia conexões de clientes, distribuição de mensagens e manutenção da lista de clientes ativos.
- **Cliente**: Permite ao usuário conectar-se ao servidor, enviar e receber mensagens.

## Implementação do Servidor

### Estrutura de Dados
- Utilizar um `ArrayList` para armazenar os clientes conectados. Cada entrada deve conter a identificação do cliente e o socket associado.

### Identificação do Cliente
- Implementar uma forma de identificação do cliente, que pode ser:
  - Um código único (exemplo: posição na lista).
  - Um nickname enviado pelo cliente.
  - Uma combinação de porta e IP do cliente.

### Funcionalidades do Servidor
1. **Envio da relação dos clientes conectados**: Permitir que os clientes requisitem e recebam uma lista dos clientes atualmente conectados.
2. **Redistribuição de mensagens para todos os clientes**: Enviar mensagens recebidas de um cliente para todos os outros clientes conectados.
3. **Envio de mensagens para um cliente específico**: Permitir o envio de mensagens para um cliente específico, identificado por seu código, nickname ou combinação de porta e IP.
4. **Remoção de cliente e fechamento do socket**: Remover um cliente da lista e fechar o socket associado quando a conexão é encerrada.

## Implementação do Cliente

### Conexão
- Ao iniciar, solicitar ao usuário um nickname (se aplicável) e conectar-se ao servidor enviando essa informação.

### Funcionalidades do Cliente
1. **Visualização da lista de clientes conectados**: Permitir que o usuário solicite e visualize uma lista de todos os clientes atualmente conectados.
2. **Envio de mensagem para um cliente específico**: Permitir que o usuário envie uma mensagem para um cliente específico.
3. **Envio de mensagem para todos os clientes conectados**: Permitir que o usuário envie uma mensagem para todos os clientes conectados.
4. **Encerramento da comunicação**: Permitir que o usuário encerre a comunicação com o servidor de forma apropriada.

### Recepção de Mensagens
- Implementar uma thread dedicada à escuta de mensagens do servidor e à apresentação dessas mensagens ao usuário.

## Como Executar

### Compilar código

```bash
javac *.java
```

### Executar servidor

```bash
java Server <port>
```

### Executar cliente

```bash
java Client <server-ip> <server-port>
```
