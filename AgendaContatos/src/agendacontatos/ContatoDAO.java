package agendacontatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    
    // Método para adicionar um contato
    public void adicionarContato(Contato contato) {
        String sql = "INSERT INTO Contatos (nome, telefone, email) VALUES (?, ?, ?)";
        try (Connection conexao = ConexaoDB.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.executeUpdate();
            System.out.println("Contato adicionado com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar contato: " + e.getMessage());
        }
    }

    // Método para listar todos os contatos
    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM Contatos";

        try (Connection conexao = ConexaoDB.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
                contatos.add(contato);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
        }
        return contatos;
    }

    // Método para atualizar um contato
    public void atualizarContato(Contato contato) {
        String sql = "UPDATE Contatos SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (Connection conexao = ConexaoDB.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setInt(4, contato.getId());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Contato atualizado com sucesso!");
            } else {
                System.out.println("Contato nao encontrado.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar contato: " + e.getMessage());
        }
    }

    // Método para remover um contato
    public void removerContato(int id) {
        String sql = "DELETE FROM Contatos WHERE id = ?";
        try (Connection conexao = ConexaoDB.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Contato removido com sucesso!");
            } else {
                System.out.println("Contato nao encontrado.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao remover contato: " + e.getMessage());
        }
    }
}
