package agendacontatos;


public class Contato {
    
    // Atributos
    private int id;
    private String nome;
    private String telefone;
    private String email;

    // Construtor vazio
    public Contato() {}

    // Construtor com parâmetros
    public Contato(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    // toString
    @Override
    public String toString() {
        return "Contato [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + "]";
    }
}