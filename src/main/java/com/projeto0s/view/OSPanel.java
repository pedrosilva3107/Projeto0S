package com.projeto0s.view;

import com.projeto0s.dao.ClienteDAO;
import com.projeto0s.dao.OSDAO;
import com.projeto0s.model.Cliente;
import com.projeto0s.model.OS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OSPanel extends JPanel {

    private ClienteDAO clienteDAO;
    private OSDAO osDAO;
    private DefaultTableModel model;
    private JTable table;
    private JComboBox<Cliente> comboCliente;

    public OSPanel(ClienteDAO clienteDAO, OSDAO osDAO) {
        this.clienteDAO = clienteDAO;
        this.osDAO = osDAO;
        setLayout(new BorderLayout(8,8));

        // Top: formulário compact
        JPanel form = new JPanel(new GridLayout(2,4,8,8));
        comboCliente = new JComboBox<>();
        refreshClientes();

        JTextField txtDescricao = new JTextField();
        JComboBox<String> comboPrioridade = new JComboBox<>(new String[]{"ALTA","MÉDIA","BAIXA"});
        JTextField txtValor = new JTextField();

        JButton btnCriar = new JButton("Criar OS");

        form.add(new JLabel("Cliente:")); form.add(comboCliente);
        form.add(new JLabel("Descrição:")); form.add(txtDescricao);
        form.add(new JLabel("Prioridade:")); form.add(comboPrioridade);
        form.add(new JLabel("Valor (R$):")); form.add(txtValor);

        add(form, BorderLayout.NORTH);
        add(btnCriar, BorderLayout.CENTER);

        // Tabela abaixo
        model = new DefaultTableModel(new String[]{"ID","Cliente","Descrição","Prioridade","Valor","Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        btnCriar.addActionListener(e -> {
            Cliente c = (Cliente) comboCliente.getSelectedItem();
            if (c == null) { JOptionPane.showMessageDialog(this,"Cadastre um cliente antes."); return; }
            String desc = txtDescricao.getText().trim();
            if (desc.isEmpty()) { JOptionPane.showMessageDialog(this,"Descrição vazia"); return; }
            String pri = (String) comboPrioridade.getSelectedItem();
            double valor = 0;
            try { if (!txtValor.getText().trim().isEmpty()) valor = Double.parseDouble(txtValor.getText().trim()); } catch (Exception ex) { JOptionPane.showMessageDialog(this,"Valor inválido"); return; }
            OS os = new OS(c, desc, pri, valor);
            osDAO.adicionar(os);
            txtDescricao.setText(""); txtValor.setText("");
            atualizar();
            JOptionPane.showMessageDialog(this,"OS criada!");
        });

        atualizar();
    }

    public void refreshClientes() {
        comboCliente.removeAllItems();
        for (Cliente c : clienteDAO.listar()) comboCliente.addItem(c);
    }

    public void atualizar() {
        refreshClientes();
        model.setRowCount(0);
        for (OS os : osDAO.listar()) {
            model.addRow(new Object[]{os.getId(), os.getCliente().getNome(), os.getDescricao(), os.getPrioridade(), os.getValor(), os.getStatus()});
        }
    }
}
