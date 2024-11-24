# Sistema de Gestão para Clínicas de Estética - Java

### Descrição

Este sistema foi desenvolvido para gerenciar as operações de uma **clínica de estética**, oferecendo funcionalidades para a gestão de **funcionários**, **procedimentos** e **disponibilidade** de horários. Ele permite o cadastro, edição e exclusão de funcionários, associando-os aos procedimentos que podem realizar e aos horários de atendimento disponíveis. O objetivo do sistema é facilitar a administração de clínicas de estética, otimizando o processo de gerenciamento interno.

### Funcionalidades:
- **Gestão de Funcionários**: 
  - Adicionar, editar, visualizar e excluir funcionários.
  - Associar funcionários aos **procedimentos** que realizam e aos **horários** em que estão disponíveis.
  
- **Gestão de Procedimentos**: 
  - Cadastro, edição e exclusão de **procedimentos** estéticos oferecidos pela clínica.
  - Visualização de todos os procedimentos registrados.

- **Gestão de Disponibilidade**: 
  - Associar funcionários a horários e turnos específicos (dias e horários de disponibilidade).

- **Relacional entre Entidades**: 
  - O sistema permite associar múltiplos procedimentos e horários a um único funcionário, criando um banco de dados dinâmico e bem estruturado para facilitar o agendamento e gestão das operações da clínica.

### Tecnologias Utilizadas:
- **Java**: Linguagem de programação utilizada para o desenvolvimento da lógica do sistema.
- **MySQL**: Banco de dados relacional para o armazenamento de dados.
- **JDBC (Java Database Connectivity)**: Conector para integração do Java com o banco de dados MySQL.
- **IDE recomendada**: IntelliJ IDEA, Eclipse ou NetBeans para desenvolvimento.

### Estrutura do Projeto:
1. **Classes e Pacotes**:
   - **Funcionario**: Responsável pelo gerenciamento dos dados dos funcionários, incluindo o nome, especialidade e procedimentos associados.
   - **Procedimento**: Lida com o cadastro, edição e visualização dos procedimentos oferecidos.
   - **Disponibilidade**: Gerencia a disponibilidade dos funcionários para atender os clientes.
   - **Banco de Dados**: Responsável pela conexão e execução de comandos SQL no banco de dados MySQL.
   - **Menu**: Interface com o usuário, onde as opções de cadastro, edição e visualização são acessadas.

2. **Operações**:
   - Cadastro, edição, exclusão e visualização dos **funcionários**, **procedimentos** e **disponibilidade**.
   - Relacionamento entre funcionários, procedimentos e disponibilidades através de tabelas intermediárias.

### Objetivo do Projeto:
O objetivo é proporcionar uma ferramenta fácil e eficiente para clínicas de estética, permitindo a gestão clara e eficaz dos funcionários, procedimentos e agendamentos. Através deste sistema, é possível melhorar a organização, reduzir o erro humano no gerenciamento de horários e procedimentos, e oferecer uma experiência mais organizada para os clientes.

### Como Usar:

1. **Configuração do Banco de Dados**:
   - Crie um banco de dados MySQL com o nome `clinica_esteticadb`.
   - Crie as tabelas necessárias para os **funcionários**, **procedimentos**, **disponibilidade** e **associações**. Exemplo de estrutura SQL para as tabelas:
     ```sql
     CREATE TABLE funcionarios (
         id INT AUTO_INCREMENT PRIMARY KEY,
         nome VARCHAR(255) NOT NULL,
         especialidade VARCHAR(255) NOT NULL
     );

     CREATE TABLE procedimentos (
         id INT AUTO_INCREMENT PRIMARY KEY,
         nome VARCHAR(255) NOT NULL,
         descricao TEXT NOT NULL,
         valor DECIMAL(10, 2) NOT NULL
     );

     CREATE TABLE disponibilidade (
         id INT AUTO_INCREMENT PRIMARY KEY,
         turno VARCHAR(255) NOT NULL,
         dia VARCHAR(255) NOT NULL
     );

     CREATE TABLE funcionarios_procedimentos (
         funcionario_id INT,
         procedimento_id INT,
         FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id),
         FOREIGN KEY (procedimento_id) REFERENCES procedimentos(id)
     );

     CREATE TABLE funcionarios_disponibilidades (
         funcionario_id INT,
         disponibilidade_id INT,
         FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id),
         FOREIGN KEY (disponibilidade_id) REFERENCES disponibilidade(id)
     );
     ```
   
2. **Configuração da Conexão JDBC**:
   - No arquivo de configuração, insira os dados da sua conexão com o banco de dados MySQL.
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/clinica_esteticadb";
     private static final String USER = "root";
     private static final String PASSWORD = "";
     ```
   - Certifique-se de que você tenha o **driver JDBC** para MySQL incluído no seu projeto.

3. **Rodando o Sistema**:
   - Compile o projeto e execute o arquivo principal que contém o **Menu** interativo.
   - O sistema vai exibir um menu de opções para que você possa adicionar, editar e visualizar os dados de funcionários, procedimentos e disponibilidades.

### Exemplo de Menu:
```plaintext
====== MENU ======
1 - Adicionar Funcionário
2 - Visualizar Funcionários
3 - Editar Funcionário
4 - Deletar Funcionário
-----------------------------------------
5 - Adicionar Procedimento
6 - Visualizar Procedimentos
7 - Editar Procedimento
8 - Deletar Procedimento
-----------------------------------------
9 - Visualizar Disponibilidades
0 - Sair
-----------------------------------------
Escolha uma opção:
