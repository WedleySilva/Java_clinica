package clinica;

import java.sql.*;
import java.util.Scanner;

public class DisponibilidadeCRUD {
    private Connection connection;

    public DisponibilidadeCRUD(Connection connection) {
        this.connection = connection;
    }

    public void visualizarDisponibilidades() {
        String sql = "SELECT * FROM disponibilidade";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String turno = rs.getString("turno");
                String dia = rs.getString("dia");
                System.out.println("ID: " + id + ", Turno: " + turno + ", Dia: " + dia);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao visualizar disponibilidades: " + e.getMessage());
        }
    }

    public void associarDisponibilidade(int funcionarioId, int disponibilidadeId) {
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
}
