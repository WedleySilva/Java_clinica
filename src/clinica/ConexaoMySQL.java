package clinica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/clinica_esteticadb";
    private static final String USER = "root";  // Altere conforme necessário
    private static final String PASSWORD = "";  // Altere conforme necessário

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro de conexão com o banco de dados: " + e.getMessage());
            return null;
        }
    }
}
