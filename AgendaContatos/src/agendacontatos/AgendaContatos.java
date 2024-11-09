package agendacontatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AgendaContatos {
    
    public static void main(String[] args) {
        
        Connection conexao = ConexaoDB.conectar();
        if (conexao != null) {
            System.out.println("Conexao bem-sucedida!");
        } else {
            System.out.println("Falha na conexao.");
        }
        
        ContatoDAO contatoDAO = new ContatoDAO();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Listar Contatos");
            System.out.println("3. Atualizar Contato");
            System.out.println("4. Remover Contato");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    contatoDAO.adicionarContato(new Contato(0, nome, telefone, email));
                    break;
                case 2:
                    System.out.println("Lista de Contatos:");
                    for (Contato c : contatoDAO.listarContatos()) {
                        System.out.println(c);
                    }
                    break;
                case 3:
                    System.out.print("ID do contato a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    System.out.print("Novo nome: ");
                    nome = scanner.nextLine();
                    System.out.print("Novo telefone: ");
                    telefone = scanner.nextLine();
                    System.out.print("Novo email: ");
                    email = scanner.nextLine();
                    contatoDAO.atualizarContato(new Contato(idAtualizar, nome, telefone, email));
                    break;
                case 4:
                    System.out.print("ID do contato a remover: ");
                    int idRemover = scanner.nextInt();
                    contatoDAO.removerContato(idRemover);
                    break;
                case 5:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 5);

        scanner.close();
        
    }
}