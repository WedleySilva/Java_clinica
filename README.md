# Sistema de Gestão de Clínica Estética

Este é um sistema de gerenciamento para clínicas de estética, onde você pode cadastrar, editar e excluir funcionários, procedimentos e suas disponibilidades. O sistema permite a associação de funcionários a procedimentos e a definição de seus horários de atendimento. 

## Funcionalidades

- **Cadastro de Funcionário**: Adicionar, editar e excluir funcionários com suas especialidades, procedimentos e horários de disponibilidade.
- **Cadastro de Procedimento**: Adicionar, editar e excluir procedimentos oferecidos na clínica.
- **Disponibilidade de Funcionário**: Gerenciar os horários em que os funcionários estão disponíveis para atender aos pacientes.

## Regras de Negócio

### 1. **Cadastro de Funcionário**

- **Regra 1.1**: O cadastro de um funcionário exige ao menos o nome e a especialidade.
- **Regra 1.2**: O nome do funcionário deve ser único para evitar duplicidade.
- **Regra 1.3**: O funcionário pode ter uma ou mais especialidades. A especialidade pode ser compartilhada por múltiplos funcionários.
- **Regra 1.4**: O cadastro de um funcionário também exige associar um ou mais procedimentos (de acordo com a especialidade do funcionário) e disponibilidades de horário.
- **Regra 1.5**: Ao criar um funcionário, o sistema automaticamente associa os procedimentos e disponibilidades informados.
  
### 2. **Cadastro de Procedimento**

- **Regra 2.1**: O cadastro de um procedimento exige os seguintes dados: nome, descrição e valor.
- **Regra 2.2**: O nome do procedimento deve ser único para evitar duplicidade de cadastro.
- **Regra 2.3**: O valor do procedimento deve ser um número positivo.
- **Regra 2.4**: Um procedimento pode ser associado a múltiplos funcionários. Cada funcionário pode realizar os procedimentos conforme sua especialidade.

### 3. **Disponibilidade de Funcionário**

- **Regra 3.1**: O cadastro de disponibilidade requer o turno (manhã, tarde, noite) e o dia da semana.
- **Regra 3.2**: Um funcionário pode ter múltiplos horários de atendimento, em diferentes turnos e dias.
- **Regra 3.3**: A disponibilidade de um funcionário não pode sobrepor-se com a de outro funcionário para o mesmo horário.

### 4. **Edição de Funcionário**

- **Regra 4.1**: Ao editar um funcionário, pode-se alterar seu nome, especialidade e associar novos procedimentos e disponibilidades.
- **Regra 4.2**: A especialidade do funcionário pode ser modificada, mas isso pode afetar os procedimentos associados.
- **Regra 4.3**: Ao editar as disponibilidades de um funcionário, ele pode ser associado a novos horários ou remover horários anteriores.

### 5. **Deletação de Funcionário**

- **Regra 5.1**: Ao deletar um funcionário, todas as associações de procedimentos e disponibilidades com esse funcionário são removidas.
- **Regra 5.2**: O sistema deve solicitar confirmação antes de excluir um funcionário, para evitar exclusões acidentais.

### 6. **Cadastro de Procedimentos**

- **Regra 6.1**: O procedimento deve ter um nome único, uma descrição detalhada e um valor.
- **Regra 6.2**: Procedimentos podem ser associados a múltiplos funcionários, desde que a especialidade do funcionário seja compatível com o procedimento.
  
### 7. **Cadastro de Disponibilidade**

- **Regra 7.1**: O sistema permite associar turnos (manhã, tarde, noite) e dias da semana aos funcionários.
- **Regra 7.2**: Um funcionário pode ter várias disponibilidades, em dias e turnos diferentes.

### 8. **Relacionamento entre Funcionários, Procedimentos e Disponibilidade**

- **Regra 8.1**: Um **funcionário** pode estar associado a múltiplos **procedimentos**.
- **Regra 8.2**: Um **procedimento** pode ser realizado por múltiplos **funcionários** (desde que estes possuam a especialidade necessária).
- **Regra 8.3**: Um **funcionário** pode ter múltiplas **disponibilidades** (vários turnos e dias).

### 9. **Validação de Dados**

- **Regra 9.1**: Ao cadastrar ou editar dados, o sistema valida que os campos obrigatórios (nome, especialidade, etc.) não estão vazios.
- **Regra 9.2**: O valor de procedimentos deve ser um número positivo. O sistema rejeitará valores negativos ou nulos.
  
## Estrutura do Banco de Dados

### Tabelas:

- **Funcionários**: Contém informações sobre os funcionários da clínica, incluindo nome e especialidade.
- **Procedimentos**: Contém informações sobre os procedimentos oferecidos pela clínica, incluindo nome, descrição e valor.
- **Disponibilidade**: Contém os horários disponíveis de cada funcionário, organizados por turno e dia da semana.
- **Funcionários_Procedimentos**: Relaciona os funcionários aos procedimentos que podem realizar, de acordo com a especialidade.
- **Funcionários_Disponibilidades**: Relaciona os funcionários aos seus horários de atendimento (turno/dia).

## Como Rodar o Projeto

1. **Instalar Dependências**
   - Você precisa ter o Python e o MySQL instalados em sua máquina.
   - Instale as dependências utilizando o seguinte comando:
     ```bash
     pip install mysql-connector-python
     ```

2. **Configuração do Banco de Dados**
   - Crie o banco de dados `clinica_esteticadb` no MySQL.
   - Execute os scripts de criação de tabelas para configurar o banco de dados.

3. **Rodar o Script**
   - Execute o script Python principal:
     ```bash
     python main.py
     ```
   - O sistema iniciará e você poderá interagir através do menu.

## Contribuições

Se você deseja contribuir para o projeto, por favor, crie uma "pull request" ou envie uma proposta para melhorias.

