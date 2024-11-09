package agendacontatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AgendaApp {
    private JFrame frame;
    private JTextField nomeField, telefoneField, emailField, idField; // Adiciona campo para ID
    private JTextArea contatosArea;

    // Configurações do banco de dados
    private static final String URL = "jdbc:postgresql://localhost:5432/agenda"; // Ajuste conforme seu banco
    private static final String USER = "postgres";  // Ajuste conforme seu usuário
    private static final String PASSWORD = "dbadmin";  // Ajuste conforme sua senha

    public AgendaApp() {
        frame = new JFrame("Agenda de Contatos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // Aumentando a altura da janela
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurações para espaçamento
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes

        // Campos de entrada com tamanhos aumentados
        nomeField = new JTextField(25); // Aumentando largura
        telefoneField = new JTextField(20); // Aumentando largura
        emailField = new JTextField(25); // Aumentando largura
        idField = new JTextField(5); // Campo para ID
        contatosArea = new JTextArea(10, 30);
        contatosArea.setEditable(false);
        contatosArea.setLineWrap(true); // Quebra de linha automática
        contatosArea.setWrapStyleWord(true); // Palavras inteiras na quebra
        JScrollPane scrollPane = new JScrollPane(contatosArea);
        scrollPane.setPreferredSize(new Dimension(400, 200)); // Aumentando a área para listar contatos

        // Botões
        JButton adicionarButton = new JButton("Adicionar Contato");
        JButton listarButton = new JButton("Listar Contatos");
        JButton removerButton = new JButton("Remover Contato"); // Botão de remover

        // Adicionar componentes ao frame
        gbc.gridx = 0; gbc.gridy = 0; // Posição (coluna, linha)
        frame.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        frame.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        frame.add(telefoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        frame.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; // ocupa 2 colunas
        frame.add(adicionarButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; // ocupa 2 colunas
        frame.add(listarButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; // ocupa 2 colunas
        frame.add(scrollPane, gbc); // Usando JScrollPane

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1; // ocupa 1 coluna
        frame.add(new JLabel("ID (para remover):"), gbc);
        gbc.gridx = 1;
        frame.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; // ocupa 2 colunas
        frame.add(removerButton, gbc);

        // Ações dos botões
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String telefone = telefoneField.getText();
                String email = emailField.getText();
                adicionarContato(nome, telefone, email); // Chama o método para adicionar contato
                // Limpar os campos de texto
                nomeField.setText("");
                telefoneField.setText("");
                emailField.setText("");
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarContatos(); // Chama o método para listar contatos
            }
        });

        removerButton.addActionListener(new ActionListener() { // Ação do botão de remover
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                removerContato(id); // Chama o método para remover contato
            }
        });

        frame.setVisible(true);
    }

    // Método para adicionar contato ao banco de dados
    private void adicionarContato(String nome, String telefone, String email) {
        String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Contato adicionado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao adicionar contato: " + e.getMessage());
        }
    }

    // Método para listar contatos do banco de dados
    private void listarContatos() {
        String sql = "SELECT * FROM contatos";
        contatosArea.setText(""); // Limpa a área de texto
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                contatosArea.append("Contato [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + "]\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao listar contatos: " + e.getMessage());
        }
    }

    // Método para remover contato do banco de dados
    private void removerContato(int id) {
        String sql = "DELETE FROM contatos WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "Contato removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(frame, "Contato não encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao remover contato: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AgendaApp();
    }
}
