import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.projeto0s.dao.ClienteDAO;
import com.projeto0s.dao.OSDAO;
import com.projeto0s.model.Cliente;
import com.projeto0s.model.OS;

public class ListarOSPanel extends JPanel {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private OSDAO osDAO = new OSDAO();

    private JTable tabelaOS;
    private JComboBox<Cliente> comboClientes;

    public ListarOSPanel() {
        setLayout(new BorderLayout());

        comboClientes = new JComboBox<>();
        carregarClientesNoCombo();

        tabelaOS = new JTable(new DefaultTableModel(new Object[]{"ID", "Descrição", "Cliente"}, 0));
        JScrollPane scrollPane = new JScrollPane(tabelaOS);

        comboClientes.addActionListener(e -> carregarOSNaTabela());

        add(comboClientes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        carregarOSNaTabela();
    }

    private void carregarClientesNoCombo() {
        List<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente cliente : clientes) {
            comboClientes.addItem(cliente);
        }
    }

    private void carregarOSNaTabela() {
        Cliente clienteSelecionado = (Cliente) comboClientes.getSelectedItem();
        List<OS> listaOS = osDAO.listarOS();

        DefaultTableModel modelo = (DefaultTableModel) tabelaOS.getModel();
        modelo.setRowCount(0);

        for (OS os : listaOS) {
            if (clienteSelecionado == null || os.getCliente().getId() == clienteSelecionado.getId()) {
                modelo.addRow(new Object[]{os.getId(), os.getDescricao(), os.getCliente().getNome()});
            }
        }
    }
}
