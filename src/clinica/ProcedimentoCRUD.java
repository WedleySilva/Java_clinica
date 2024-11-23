package clinica;

import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;


public class ProcedimentoCRUD {
    private Connection connection;

    public ProcedimentoCRUD(Connection connection) {
        this.connection = connection;
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
                System.out.println("ID: " + id + ", Nome: " + nome + ", Descrição: " + descricao + ", Valor: " + valor);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao visualizar procedimentos: " + e.getMessage());
        }
    }

    public void editarProcedimento(Scanner scanner) {
        System.out.print("Digite o ID do procedimento para editar: ");
        int procedimentoId = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        System.out.print("Novo Nome do Procedimento: ");
        String novoNome = scanner.nextLine();
        System.out.print("Nova Descrição do Procedimento: ");
        String novaDescricao = scanner.nextLine();
        System.out.print("Novo Valor do Procedimento: ");
        float novoValor = scanner.nextFloat();

        String sql = "UPDATE procedimentos SET nome = ?, descricao = ?, valor = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novaDescricao);
            stmt.setFloat(3, novoValor);
            stmt.setInt(4, procedimentoId);
            stmt.executeUpdate();
            System.out.println("Procedimento atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao editar procedimento: " + e.getMessage());
        }
    }

    public void deletarProcedimento(Scanner scanner) {
        System.out.print("Digite o ID do procedimento para deletar: ");
        int procedimentoId = scanner.nextInt();

        String sql = "DELETE FROM procedimentos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, procedimentoId);
            stmt.executeUpdate();
            System.out.println("Procedimento deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar procedimento: " + e.getMessage());
        }
    }
}
