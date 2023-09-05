import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaContatos {

    private ListaContatos listaContatos;
    private JTextField pesquisaTextField;
    private DefaultTableModel tableModel;

    public TelaContatos(ListaContatos listaContatos) {
        this.listaContatos = listaContatos;
    }

    public void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lista de Contatos");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 700);

            JPanel panel = new JPanel(new BorderLayout());

            // Campo de pesquisa
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pesquisaTextField = new JTextField(20);
            JButton pesquisarButton = new JButton("Pesquisar");

            pesquisarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String termoPesquisa = pesquisaTextField.getText();
                    pesquisarContatos(termoPesquisa);
                }
            });

            searchPanel.add(new JLabel("Pesquisar por Nome/CPF:"));
            searchPanel.add(pesquisaTextField);
            searchPanel.add(pesquisarButton);

            // Planilha de contatos
            String[] colunas = {"Nome", "Telefone", "CEP", "CPF"};
            tableModel = new DefaultTableModel(colunas, 0);
            JTable table = new JTable(tableModel);
            JScrollPane tableScrollPane = new JScrollPane(table);

            // Botões
            JButton voltarButton = new JButton("Voltar");
            JButton salvarButton = new JButton("Salvar");

            voltarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new TelaPrincipal(listaContatos).createAndShowGUI();
                }
            });

            panel.add(searchPanel, BorderLayout.NORTH);
            panel.add(tableScrollPane, BorderLayout.CENTER);

            // Botão "Salvar"
            panel.add(salvarButton, BorderLayout.PAGE_END);

            // Botão "Voltar"
            panel.add(voltarButton, BorderLayout.PAGE_END);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private void pesquisarContatos(String termoPesquisa) {
        List<Contato> contatos = listaContatos.pesquisarContato(termoPesquisa);
        tableModel.setRowCount(0);

        for (Contato contato : contatos) {
            String[] rowData = {contato.getNome(), contato.getTelefone(), contato.getEndereco(), contato.getCpf()};
            tableModel.addRow(rowData);
        }
    }
}
