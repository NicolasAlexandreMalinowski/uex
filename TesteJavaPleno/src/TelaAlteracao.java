import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaAlteracao {

    private ListaContatos listaContatos;
    private DefaultTableModel tableModel;
    private JTextField nomeTextField;
    private JTextField telefoneTextField;
    private JTextField cepTextField;
    private JTextField cpfTextField;
    private boolean alteracoesSalvas;

    public TelaAlteracao(ListaContatos listaContatos) {
        this.listaContatos = listaContatos;
        this.alteracoesSalvas = true;
    }

    public void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tela de Alteração de Contatos");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 700);

            JPanel panel = new JPanel(new BorderLayout());

            // Campo de pesquisa
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextField pesquisaTextField = new JTextField(20);
            JButton searchButton = new JButton("Pesquisar");

            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchTerm = pesquisaTextField.getText();
                    pesquisarContatos(searchTerm);
                }
            });

            searchPanel.add(new JLabel("Pesquisar por Nome/CPF:"));
            searchPanel.add(pesquisaTextField);
            searchPanel.add(searchButton);

            // Planilha de contatos editável
            String[] colunas = {"Nome", "Telefone", "CEP", "CPF"};
            tableModel = new DefaultTableModel(colunas, 0);
            JTable table = new JTable(tableModel);
            JScrollPane tableScrollPane = new JScrollPane(table);

            // Campos de edição
            nomeTextField = new JTextField(20);
            telefoneTextField = new JTextField(20);
            cepTextField = new JTextField(20);
            cpfTextField = new JTextField(20);

            // Botões
            JButton editarButton = new JButton("Editar");
            JButton salvarButton = new JButton("Salvar");
            JButton voltarButton = new JButton("Voltar");

            editarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Preencha os campos de edição com os dados da linha selecionada
                        nomeTextField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        telefoneTextField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        cepTextField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        cpfTextField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    }
                }
            });

            salvarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Atualize os dados na tabela
                        tableModel.setValueAt(nomeTextField.getText(), selectedRow, 0);
                        tableModel.setValueAt(telefoneTextField.getText(), selectedRow, 1);
                        tableModel.setValueAt(cepTextField.getText(), selectedRow, 2);
                        tableModel.setValueAt(cpfTextField.getText(), selectedRow, 3);

                        // Marque as alterações como não salvas
                        alteracoesSalvas = false;
                    }
                }
            });

            voltarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!alteracoesSalvas) {
                        int resposta = JOptionPane.showConfirmDialog(frame, "Deseja salvar as alterações?");
                        if (resposta == JOptionPane.YES_OPTION) {
                            // Salve as alterações se o usuário optar por fazê-lo
                            salvarAlteracoes();
                        }
                    }
                    frame.dispose();
                    new TelaPrincipal(listaContatos).createAndShowGUI();
                }
            });

            panel.add(searchPanel, BorderLayout.NORTH);
            panel.add(tableScrollPane, BorderLayout.CENTER);

            // Painel de edição
            JPanel editPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            editPanel.add(new JLabel("Nome:"));
            editPanel.add(nomeTextField);
            editPanel.add(new JLabel("Telefone:"));
            editPanel.add(telefoneTextField);
            editPanel.add(new JLabel("CEP:"));
            editPanel.add(cepTextField);
            editPanel.add(new JLabel("CPF:"));
            editPanel.add(cpfTextField);
            editPanel.add(editarButton);
            editPanel.add(salvarButton);

            panel.add(editPanel, BorderLayout.SOUTH);

            // Botão Voltar
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

    private void salvarAlteracoes() {
        int rowCount = tableModel.getRowCount();
        List<Contato> contatosAtualizados = listaContatos.getContatos();

        for (int i = 0; i < rowCount; i++) {
            String nome = tableModel.getValueAt(i, 0).toString();
            String telefone = tableModel.getValueAt(i, 1).toString();
            String cep = tableModel.getValueAt(i, 2).toString();
            String cpf = tableModel.getValueAt(i, 3).toString();

            Contato contato = contatosAtualizados.get(i);
            contato.setNome(nome);
            contato.setTelefone(telefone);
            contato.setEndereco(cep);
            contato.setCpf(cpf);
        }

        listaContatos.salvarContatosEmArquivo();
        JOptionPane.showMessageDialog(null, "Alterações salvas com sucesso!");
        alteracoesSalvas = true;
    }
}
