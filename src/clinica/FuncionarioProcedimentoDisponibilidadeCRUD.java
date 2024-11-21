package clinica;

import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;


public class FuncionarioProcedimentoDisponibilidadeCRUD {
    private Connection connection;

    public FuncionarioProcedimentoDisponibilidadeCRUD(Connection connection) {
        this.connection = connection;
    }

    public void adicionarFuncionario(Scanner scanner) {
        System.out.print("Nome do Funcionário: ");
        String nome = scanner.nextLine();
        System.out.print("Especialidade do Funcionário: ");
        String especialidade = scanner.nextLine();

        String sql = "INSERT INTO funcionarios (nome, especialidade) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.setString(2, especialidade);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int funcionarioId = generatedKeys.getInt(1);
                System.out.println("Funcionário adicionado com sucesso! ID: " + funcionarioId);
                associarProcedimentosDisponibilidades(funcionarioId, scanner);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar funcionário: " + e.getMessage());
        }
    }

    private void associarProcedimentosDisponibilidades(int funcionarioId, Scanner scanner) {
        System.out.println("Associando procedimentos e disponibilidades ao funcionário...");

        // Associar procedimentos
        System.out.print("Deseja associar procedimentos? (s/n): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Digite os IDs dos procedimentos separados por vírgula: ");
            String[] procedimentoIds = scanner.nextLine().split(",");
            for (String id : procedimentoIds) {
                associarProcedimento(funcionarioId, Integer.parseInt(id.trim()));
            }
        }

        // Associar disponibilidades
        System.out.print("Deseja associar disponibilidades? (s/n): ");
        resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Digite os IDs das disponibilidades separados por vírgula: ");
            String[] disponibilidadeIds = scanner.nextLine().split(",");
            for (String id : disponibilidadeIds) {
                associarDisponibilidade(funcionarioId, Integer.parseInt(id.trim()));
            }
        }
    }

    private void associarProcedimento(int funcionarioId, int procedimentoId) {
        String sql = "INSERT INTO funcionarios_procedimentos (funcionario_id, procedimentos_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, procedimentoId);
            stmt.executeUpdate();
            System.out.println("Procedimento associado ao funcionário!");
        } catch (SQLException e) {
            System.out.println("Erro ao associar procedimento: " + e.getMessage());
        }
    }

    private void associarDisponibilidade(int funcionarioId, int disponibilidadeId) {
        String sql = "INSERT INTO funcionarios_disponibilidades (funcionario_id, disponibilidade_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, disponibilidadeId);
            stmt.executeUpdate();
            System.out.println("Disponibilidade associada ao funcionário!");
        } catch (SQLException e) {
            System.out.println("Erro ao associar disponibilidade: " + e.getMessage());
        }
    }

    public void visualizarFuncionarios() {
        String sql = "SELECT * FROM funcionarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String especialidade = rs.getString("especialidade");
                System.out.println(new Funcionario(id, nome, especialidade));
                visualizarProcedimentosDisponibilidades(id);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao visualizar funcionários: " + e.getMessage());
        }
    }

    private void visualizarProcedimentosDisponibilidades(int funcionarioId) {
        // Consulta para visualizar procedimentos associados ao funcionário
        String sqlProcedimentos = "SELECT p.id, p.nome FROM procedimentos p " +
                "JOIN funcionarios_procedimentos fp ON p.id = fp.procedimentos_id " +
                "WHERE fp.funcionario_id = ?";

        // Consulta para visualizar disponibilidades associadas ao funcionário
        String sqlDisponibilidades = "SELECT d.id, d.turno, d.dia FROM disponibilidade d " +
                "JOIN funcionarios_disponibilidades fd ON d.id = fd.disponibilidade_id " +
                "WHERE fd.funcionario_id = ?";

        try (PreparedStatement stmtProc = connection.prepareStatement(sqlProcedimentos)) {
            stmtProc.setInt(1, funcionarioId);
            ResultSet rsProc = stmtProc.executeQuery();
            System.out.println("Procedimentos associados:");
            while (rsProc.next()) {
                int procedimentoId = rsProc.getInt("id");
                String procedimentoNome = rsProc.getString("nome");
                System.out.println("ID: " + procedimentoId + ", Nome: " + procedimentoNome);
            }

            try (PreparedStatement stmtDisp = connection.prepareStatement(sqlDisponibilidades)) {
                stmtDisp.setInt(1, funcionarioId);
                ResultSet rsDisp = stmtDisp.executeQuery();
                System.out.println("Disponibilidades associadas:");
                while (rsDisp.next()) {
                    int disponibilidadeId = rsDisp.getInt("id");
                    String turno = rsDisp.getString("turno");
                    String dia = rsDisp.getString("dia");
                    System.out.println("ID: " + disponibilidadeId + ", Turno: " + turno + ", Dia: " + dia);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao visualizar procedimentos e disponibilidades: " + e.getMessage());
        }
    }

    public void adicionarProcedimento(Scanner scanner) {
        System.out.print("Nome do Procedimento: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do Procedimento: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor do Procedimento: ");

        float valor = 0.0f;
        try {
            valor = scanner.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Erro: O valor do procedimento deve ser um número.");
            scanner.nextLine(); // Limpar o buffer
        }

        String sql = "INSERT INTO procedimentos (nome, descricao, valor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setFloat(3, valor);
            stmt.executeUpdate();
            System.out.println("Procedimento inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar procedimento: " + e.getMessage());
        }
    }

    public void visualizarProcedimentos() {
        String sql = "SELECT * FROM procedimentos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                float valor = rs.getFloat("valor");
                System.out.println(new Procedimento(id, nome, descricao, valor));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao visualizar procedimentos: " + e.getMessage());
        }
    }
}
