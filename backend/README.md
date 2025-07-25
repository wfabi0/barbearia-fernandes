# Backend - Barbearia Fernandes

Este guia explica como configurar e rodar o backend do projeto Barbearia Fernandes utilizando o IntelliJ IDEA.

## Pré-requisitos

- Java 17 ou superior instalado
- MySQL 8 instalado e rodando
- IntelliJ IDEA (Community ou Ultimate)
- Maven configurado (o IntelliJ já possui Maven embutido)

## Configurando o Backend no IntelliJ IDEA

1. **Abra o projeto no IntelliJ IDEA**
    - Clique em `File > Open...` e selecione a pasta `backend`.

2. **Configure o banco de dados**
    - No arquivo `src/main/resources/application.properties`, ajuste as configurações de conexão do MySQL conforme seu ambiente:
      ```
      spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco
      spring.datasource.username=seu_usuario
      spring.datasource.password=sua_senha
      ```
    - Crie o banco de dados no MySQL se ainda não existir.

3. **Configurar execução pelo IntelliJ**
    - No canto superior direito do IntelliJ, clique na seta ao lado do botão verde de "Run" e selecione `Edit Configurations...`.
    - Clique no botão `+` no canto superior esquerdo da janela.
    - Selecione `Maven`.
    - Em **Name**, coloque por exemplo: `Spring Boot Run`.
    - Em **Command line**, digite:
      ```
      spring-boot:run
      ```
    - Clique em `OK` para salvar.

4. **Rodando o backend**
    - Selecione a configuração `Spring Boot Run` no menu suspenso ao lado do botão verde de "Run".
    - Clique no botão verde de "Run" (ou pressione `Shift + F10`).
    - O backend será iniciado e ficará disponível na porta configurada (por padrão, `http://localhost:8080`).

## Observações

- Certifique-se de que o MySQL está rodando antes de iniciar o backend.
- Para parar a aplicação, clique no botão vermelho de "Stop" no IntelliJ.