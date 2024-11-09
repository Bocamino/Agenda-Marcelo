package agendacontatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/agenda"; // Colocar nome da database criada no dbAdmin4
    private static final String USER = "postgres";  // Substitua pelo seu usuário PostgreSQL
    private static final String PASSWORD = "dbadmin";  // Substitua pela sua senha2

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro na conexao: " + e.getMessage());
            return null;
        }
    }
    
}
