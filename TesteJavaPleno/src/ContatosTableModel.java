import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ContatosTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private List<Contato> contatos;
    private String[] colunas;

    public ContatosTableModel(List<Contato> contatos, String[] colunas) {
        this.contatos = contatos;
        this.colunas = colunas;
    }

    @Override
    public int getRowCount() {
        return contatos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contato contato = contatos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return contato.getNome();
            case 1:
                return contato.getTelefone();
            case 2:
                return contato.getEmail();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }
}
