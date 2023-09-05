import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TelaCadastro {

    private ListaContatos listaContatos;
    private JTextField nomeTextField;
    private JTextField telefoneTextField;
    private JTextField cepTextField;
    private JTextField cpfTextField;
    private JTextArea enderecoTextArea;

    public TelaCadastro(ListaContatos listaContatos) {
        this.listaContatos = listaContatos;
    }

    public void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cadastro de Contato");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 700);

            JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

            JLabel nomeLabel = new JLabel("Nome:");
            nomeTextField = new JTextField(20);

            JLabel telefoneLabel = new JLabel("Telefone:");
            telefoneTextField = new JTextField(20);

            JLabel cepLabel = new JLabel("CEP:");
            cepTextField = new JTextField(20);

            JLabel cpfLabel = new JLabel("CPF:");
            cpfTextField = new JTextField(20);

            JLabel usuarioLabel = new JLabel("Nome de Usuário:");
            JTextField usuarioTextField = new JTextField(20);

            JLabel senhaLabel = new JLabel("Senha:");
            JPasswordField senhaField = new JPasswordField(20);

            JButton salvarButton = new JButton("Salvar");
            JButton voltarButton = new JButton("Voltar");

            salvarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String nome = nomeTextField.getText();
                    String telefone = telefoneTextField.getText();
                    String cep = cepTextField.getText();
                    String cpf = cpfTextField.getText();
                    String usuario = usuarioTextField.getText();
                    char[] senhaChars = senhaField.getPassword();
                    String senha = new String(senhaChars);

                    // Verifique a disponibilidade do nome de usuário
                    if (verificarDisponibilidadeUsuario(usuario)) {
                        // Obtenha o endereço do Via CEP
                        String endereco = obterEnderecoViaCEP(cep);

                        Contato novoContato = new Contato(nome, telefone, endereco, cpf);
                        listaContatos.adicionarContato(novoContato);

                        // Limpe os campos após salvar
                        nomeTextField.setText("");
                        telefoneTextField.setText("");
                        cepTextField.setText("");
                        cpfTextField.setText("");
                        enderecoTextArea.setText("");
                        usuarioTextField.setText("");
                        senhaField.setText("");

                        JOptionPane.showMessageDialog(frame, "Contato salvo com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Esse nome de usuário está indisponível!");
                    }
                }
            });

            voltarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new TelaPrincipal(listaContatos).createAndShowGUI();
                }
            });

            // Text area para mostrar o endereço
            enderecoTextArea = new JTextArea(3, 20);
            enderecoTextArea.setWrapStyleWord(true);
            enderecoTextArea.setLineWrap(true);
            enderecoTextArea.setOpaque(false);
            enderecoTextArea.setEditable(false);
            JScrollPane enderecoScrollPane = new JScrollPane(enderecoTextArea);

            panel.add(nomeLabel);
            panel.add(nomeTextField);
            panel.add(telefoneLabel);
            panel.add(telefoneTextField);
            panel.add(cepLabel);
            panel.add(cepTextField);
            panel.add(cpfLabel);
            panel.add(cpfTextField);
            panel.add(usuarioLabel);
            panel.add(usuarioTextField);
            panel.add(senhaLabel);
            panel.add(senhaField);
            panel.add(enderecoScrollPane); // Adicione o campo de endereço
            panel.add(salvarButton);
            panel.add(voltarButton);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    // Método para verificar a disponibilidade do nome de usuário
    private boolean verificarDisponibilidadeUsuario(String usuario) {
        // Verifique se o usuário já existe (substitua isso com sua própria lógica)
        // Se já existir, retorne false; caso contrário, retorne true
        return !listaContatos.usuarioExistente(usuario);
    }

    // Método para obter o endereço usando o Via CEP
    private String obterEnderecoViaCEP(String cep) {
        try {
            ViaCEPClient client = new ViaCEPClient();
            ViaCEPResponse response = client.getEndereco(cep);
            if (response != null) {
                return response.getLogradouro() + ", " + response.getBairro() + ", " + response.getLocalidade() + " - " + response.getUf();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Endereço não encontrado";
    }
}
