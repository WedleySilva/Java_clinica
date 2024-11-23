package clinica;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = ConexaoMySQL.conectar();

        FuncionarioCRUD funcionarioCrud = new FuncionarioCRUD(connection);
        ProcedimentoCRUD procedimentoCrud = new ProcedimentoCRUD(connection);
        DisponibilidadeCRUD disponibilidadeCrud = new DisponibilidadeCRUD(connection);

        while (true) {
            System.out.println("====== MENU ======");
            System.out.println("1 - Adicionar Funcionário");
            System.out.println("2 - Visualizar Funcionários");
            System.out.println("3 - Editar Funcionário");
            System.out.println("4 - Deletar Funcionário");
            System.out.println("-----------------------------------------");
            System.out.println("5 - Adicionar Procedimento");
            System.out.println("6 - Visualizar Procedimentos");
            System.out.println("7 - Editar Procedimento");
            System.out.println("8 - Deletar Procedimento");
            System.out.println("-----------------------------------------");
            System.out.println("9 - Vizualizar Disponibilidades");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            switch (opcao) {
                case 1:
                    funcionarioCrud.adicionarFuncionario(scanner);
                    break;
                case 2:
                    funcionarioCrud.visualizarFuncionarios();
                    break;
                case 3:
                    funcionarioCrud.editarFuncionario(scanner);
                    break;
                case 4:
                    funcionarioCrud.deletarFuncionario(scanner);
                    break;
                case 5:
                    procedimentoCrud.adicionarProcedimento(scanner);
                    break;
                case 6:
                    procedimentoCrud.visualizarProcedimentos();
                    break;
                case 7:
                    procedimentoCrud.editarProcedimento(scanner);
                    break;
                case 8:
                    procedimentoCrud.deletarProcedimento(scanner);
                    break;
                case 9:
                    disponibilidadeCrud.visualizarDisponibilidades();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
