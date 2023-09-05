import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListaContatos implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Contato> contatos;
    private List<String> usuarios;

    public ListaContatos() {
        contatos = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void adicionarContato(Contato contato) {
        contatos.add(contato);
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void salvarContatosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contatos.ser"))) {
            oos.writeObject(contatos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarContatosDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contatos.ser"))) {
            contatos = (List<Contato>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean usuarioExistente(String usuario) {
        return usuarios.contains(usuario);
    }
}
