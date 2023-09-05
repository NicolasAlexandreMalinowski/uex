import java.io.Serializable;

public class Contato implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String telefone;
    private String email;
    private String cep; // Adicionado campo CEP
    private String cpf; // Adicionado campo CPF

    public Contato(String nome, String telefone, String email, String cep, String cpf) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getCep() {
        return cep;
    }

    public String getCpf() {
        return cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Telefone: " + telefone + ", Email: " + email + ", CEP: " + cep + ", CPF: " + cpf;
    }
}
