package clinica;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = ConexaoMySQL.conectar();

        FuncionarioProcedimentoDisponibilidadeCRUD crud = new FuncionarioProcedimentoDisponibilidadeCRUD(connection);

        while (true) {
            System.out.println("====== MENU ======");
            System.out.println("1 - Adicionar Funcionário");
            System.out.println("2 - Visualizar Funcionários");
            System.out.println("3 - Editar Funcionário");
            System.out.println("4 - Deletar Funcionário");
            System.out.println("5 - Adicionar Procedimento");
            System.out.println("6 - Visualizar Procedimentos");
            System.out.println("7 - Editar Procedimento");
            System.out.println("8 - Deletar Procedimento");
            System.out.println("9 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            switch (opcao) {
                case 1:
                    crud.adicionarFuncionario(scanner);
                    break;
                case 2:
                    crud.visualizarFuncionarios();
                    break;
                case 5:
                    crud.adicionarProcedimento(scanner);
                    break;
                case 6:
                    crud.visualizarProcedimentos();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
