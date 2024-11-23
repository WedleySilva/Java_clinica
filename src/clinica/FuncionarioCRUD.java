package clinica;

import java.sql.*;
import java.util.Scanner;

public class FuncionarioCRUD {
    private Connection connection;

    public FuncionarioCRUD(Connection connection) {
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

                // Agora, associar múltiplas disponibilidades ao funcionário
                System.out.println("Associando disponibilidades ao funcionário...");
                associarMultiplaDisponibilidade(scanner, funcionarioId);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar funcionário: " + e.getMessage());
        }
    }

    private void associarMultiplaDisponibilidade(Scanner scanner, int funcionarioId) {
        // Visualizar as disponibilidades
        String sqlDisponibilidades = "SELECT * FROM disponibilidade";
        try (PreparedStatement stmt = connection.prepareStatement(sqlDisponibilidades);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Disponibilidades disponíveis:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String turno = rs.getString("turno");
                String dia = rs.getString("dia");
                System.out.println("ID: " + id + ", Turno: " + turno + ", Dia: " + dia);
            }

            // Permitir que o usuário associe múltiplas disponibilidades
            System.out.println("Digite os IDs das disponibilidades a serem associadas (separados por vírgula):");
            String[] disponibilidadeIds = scanner.nextLine().split(",");

            for (String idStr : disponibilidadeIds) {
                try {
                    int disponibilidadeId = Integer.parseInt(idStr.trim());
                    // Associar a disponibilidade ao funcionário
                    String sqlAssociar = "INSERT INTO funcionarios_disponibilidades (funcionario_id, disponibilidade_id) VALUES (?, ?)";
                    try (PreparedStatement stmtAssoc = connection.prepareStatement(sqlAssociar)) {
                        stmtAssoc.setInt(1, funcionarioId);
                        stmtAssoc.setInt(2, disponibilidadeId);
                        stmtAssoc.executeUpdate();
                        System.out.println("Disponibilidade ID " + disponibilidadeId + " associada com sucesso ao funcionário!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de disponibilidade inválido: " + idStr);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao associar disponibilidades: " + e.getMessage());
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
        // Consultar procedimentos
        String sqlProcedimentos = "SELECT p.id, p.nome FROM procedimentos p " +
                "JOIN funcionarios_procedimentos fp ON p.id = fp.procedimentos_id " +
                "WHERE fp.funcionario_id = ?";

        // Consultar disponibilidades
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

    public void editarFuncionario(Scanner scanner) {
        System.out.print("Digite o ID do funcionário para editar: ");
        int funcionarioId = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        System.out.print("Novo Nome do Funcionário: ");
        String novoNome = scanner.nextLine();
        System.out.print("Nova Especialidade do Funcionário: ");
        String novaEspecialidade = scanner.nextLine();

        String sql = "UPDATE funcionarios SET nome = ?, especialidade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novaEspecialidade);
            stmt.setInt(3, funcionarioId);
            stmt.executeUpdate();
            System.out.println("Funcionário atualizado com sucesso!");

            // Editar as disponibilidades associadas ao funcionário
            System.out.println("Editando disponibilidades associadas ao funcionário...");
            editarMultiplaDisponibilidade(scanner, funcionarioId);
        } catch (SQLException e) {
            System.out.println("Erro ao editar funcionário: " + e.getMessage());
        }
    }

    private void editarMultiplaDisponibilidade(Scanner scanner, int funcionarioId) {
        System.out.println("Deseja editar as disponibilidades associadas? (S/N)");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            // Remover disponibilidades antigas
            String sqlDeletar = "DELETE FROM funcionarios_disponibilidades WHERE funcionario_id = ?";
            try (PreparedStatement stmtDel = connection.prepareStatement(sqlDeletar)) {
                stmtDel.setInt(1, funcionarioId);
                stmtDel.executeUpdate();
                System.out.println("Disponibilidades anteriores removidas.");

                // Adicionar novas disponibilidades
                associarMultiplaDisponibilidade(scanner, funcionarioId);
            } catch (SQLException e) {
                System.out.println("Erro ao editar disponibilidades: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhuma modificação realizada nas disponibilidades.");
        }
    }

    public void deletarFuncionario(Scanner scanner) {
        System.out.print("Digite o ID do funcionário para deletar: ");
        int funcionarioId = scanner.nextInt();

        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            stmt.executeUpdate();
            System.out.println("Funcionário deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar funcionário: " + e.getMessage());
        }
    }
}
