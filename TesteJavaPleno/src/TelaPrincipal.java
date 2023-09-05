import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal {

    private ListaContatos listaContatos;

    public TelaPrincipal(ListaContatos listaContatos) {
        this.listaContatos = listaContatos;
        listaContatos.carregarContatosDeArquivo(); // Carrega contatos ao iniciar o programa
    }

    public void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tela Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 700);

            JPanel panel = new JPanel(new BorderLayout());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            Dimension buttonSize = new Dimension(200, 50);

            JButton cadastrarBotao = new JButton("Cadastrar Novo Contato");
            JButton alterarBotao = new JButton("Alterar Contato");
            JButton contatosBotao = new JButton("Contatos");
            JButton verMapaBotao = new JButton("Ver Mapa");

            cadastrarBotao.setPreferredSize(buttonSize);
            alterarBotao.setPreferredSize(buttonSize);
            contatosBotao.setPreferredSize(buttonSize);
            verMapaBotao.setPreferredSize(buttonSize);

            cadastrarBotao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new TelaCadastro(listaContatos).createAndShowGUI();
                }
            });

            alterarBotao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new TelaAlteracao(listaContatos).createAndShowGUI();
                }
            });

            contatosBotao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new TelaContatos(listaContatos).createAndShowGUI();
                }
            });

            verMapaBotao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new TelaMapa(listaContatos).createAndShowGUI();
                }
            });

            buttonPanel.add(cadastrarBotao);
            buttonPanel.add(alterarBotao);
            buttonPanel.add(contatosBotao);
            buttonPanel.add(verMapaBotao);

            panel.add(buttonPanel, BorderLayout.NORTH);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        ListaContatos listaContatos = new ListaContatos();
        TelaPrincipal telaPrincipal = new TelaPrincipal(listaContatos);
        telaPrincipal.createAndShowGUI();
    }
}
