package clinica;

import java.util.Scanner;  // Adicionando o import do Scanner

public interface CrudOperations<T> {
    void adicionar(Scanner scanner);
    void visualizar();
    void editar(Scanner scanner);
    void deletar(Scanner scanner);
}
